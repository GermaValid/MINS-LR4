# LR4 — распределённая система кафе (gRPC)

Проект разбит на два микросервиса по методичке: **A (ядро)** и **B (справочник)**. Между ними — только **gRPC**. Ниже по шагам расписано, **что за чем выполняется** при работе системы.

---

## 1. Структура репозитория (что за папка)

1. **`pom.xml` в корне** — родительский Maven-проект (`packaging` = `pom`). Собирает четыре подмодуля по порядку зависимостей.
2. **`common/`** — общий Java-код домена **меню**: загрузка CSV, сущности `Menu`, `Dish`, фабрика, исключение `ValidationException`. Ни gRPC, ни сети здесь нет.
3. **`grpc-api/`** — контракт **Protocol Buffers** (`src/main/proto/menu_catalog.proto`) и ручной класс **`TraceMetadata`**: имя HTTP-стиля заголовка `x-trace-id` для передачи trace между сервисами. При `mvn compile` плагин генерирует Java-классы сообщений и `MenuCatalogGrpc` в `target/generated-sources`.
4. **`reference-service/`** — **сервис B**: поднимает gRPC-сервер, держит в памяти меню из CSV и отдаёт его по RPC.
5. **`core-service/`** — **сервис A**: консольное приложение, заказ, оплата; **меню всегда запрашивается у B** по gRPC.

---

## 2. Контракт gRPC (файл `grpc-api/src/main/proto/menu_catalog.proto`)

Построчно по смыслу контракта:

1. **`service MenuCatalog`** — логический «справочник меню» на стороне B.
2. **`rpc GetMenu`** — без аргументов (пустой `GetMenuRequest`); ответ `GetMenuResponse` содержит список **`DishItem`**: имя, цена, описание. Сервис A вызывает это, чтобы **полностью пересобрать** локальный объект `Menu` в памяти процесса A.
3. **`rpc ValidateDishNumber`** — на вход номер позиции (1-based, как в консоли); ответ: признак `valid` и при необходимости `error_message`. В текущей версии консольного сценария A **в основном полагается на локальный `Menu` после GetMenu**; RPC оставлен как часть справочника/валидации по ТЗ.

---

## 3. Запуск системы (два процесса)

**Важно:** модули `reference-service` и `core-service` зависят от **`common`** и **`grpc-api`**. Они **не лежат в Maven Central** — их нужно сначала **собрать** из этого репозитория (чтобы JAR попали в локальный `~/.m2`).

- Флаг **`-am`** (*also-make*) удобен для **сборки** (`package` / `install`): Maven соберёт `common`, `grpc-api` и выбранный модуль.
- **Нельзя** одной командой делать **`mvn -pl … -am exec:java`** из корня: в реактор попадает корневой проект с **`packaging` = `pom`**, и `exec-maven-plugin` первым пытается запустить Java **на нём** → ошибка *parameters 'mainClass' … are missing*.

**Как запускать (надёжно):** два шага — сначала **только сборка** с `-am`, потом **`exec:java` без `-am`** (тогда в реакторе один модуль с `mainClass`).

Предупреждения про **`sun.misc.Unsafe`** при старте Maven на новых JDK — из Guice/Maven, **на работу лабораторной не влияют**.

### 3.1. Сервис B (справочник) — первым

1. Из корня **`lr4`** (два шага подряд):

   ```bash
   mvn -pl reference-service -am package
   mvn -pl reference-service exec:java
   ```

   **Альтернатива:** один раз выполнить **`mvn install`** из корня (соберёт все модули), дальше для повторных запусков B достаточно **`mvn -pl reference-service exec:java`** (без `-am`).

   **Если в логе `Address already in use` / порт 50051 занят:** уже запущен другой экземпляр B (или зависший процесс после `exec:java`). Закройте тот терминал / завершите `java.exe` в диспетчере задач **или** задайте свободный порт, например в PowerShell перед запуском B и A:

   ```powershell
   $env:REFERENCE_GRPC_PORT = "50052"
   mvn -pl reference-service exec:java
   ```

   (Во втором окне для A задайте **тот же** `REFERENCE_GRPC_PORT`.)

2. Точка входа: класс **`ru.bmstu.iu3.reference.ReferenceMenuApplication`**, метод `main`.
3. Читается порт: переменная окружения **`REFERENCE_GRPC_PORT`**, иначе строка **`50051`**.
4. Вызывается **`MenuBootstrap.loadFromClasspath()`** (модуль `common`): ищется ресурс **`/data/menu.csv`**, парсинг через **`MenuCsvLoader`** в **`MenuUnitOfWork`**; при ошибке подставляется небольшой запасной список блюд.
5. Создаётся объект **`Menu`** с уже заполненным списком блюд.
6. Собирается gRPC-сервер **`NettyServerBuilder`**: слушает **`0.0.0.0:порт`**, регистрируется реализация **`MenuCatalogGrpcServiceImpl`**, цепочка перехватчиков включает **`TraceServerInterceptor`**.
7. **`server.start()`** — сервер начинает принимать TCP-соединения и gRPC-кадры.
8. В консоль печатается строка вида **`[reference-service] gRPC сервер запущен на 0.0.0.0:…`**.
9. **`server.awaitTermination()`** — поток `main` блокируется, процесс живёт, пока его не остановят.

### 3.2. Сервис A (ядро) — вторым

1. Во **втором** терминале, из того же корня **`lr4`**:

   ```bash
   mvn -pl core-service -am package
   mvn -pl core-service exec:java
   ```

   Если уже делали **`mvn install`** из корня, достаточно **`mvn -pl core-service exec:java`**.

   Сначала должен быть запущен B, иначе при запросе меню увидите сообщение о недоступности справочника.
2. Точка входа: **`ru.bmstu.iu3.App`**, метод `main`.
3. Читается адрес B: **`REFERENCE_GRPC_HOST`** (по умолчанию **`127.0.0.1`**) и порт **`REFERENCE_GRPC_PORT`** (по умолчанию **`50051`**).
4. Создаётся **`Reader`** — ввод с консоли (`Scanner`).
5. Создаётся **`MenuCatalogGrpcClient(host, port)`**:
   - внутри — **`ManagedChannelBuilder.forAddress(...).usePlaintext().build()`** (без TLS, учебный режим);
   - блокирующий stub **`MenuCatalogGrpc.newBlockingStub(channel).withInterceptors(new TraceClientInterceptor())`** — каждый вызов RPC может добавить метаданные.
6. Создаётся пустой доменный **`Order`**, список способов оплаты (**`CardPayment`**, **`CashPayment`**).
7. Создаётся **`MenuService`**, в него передаётся **только** клиент каталога (локального чтения CSV в A больше нет).
8. Создаётся **`OrderService`**: ссылки на **`Order`**, **`menuService.getMenu()`** (тот же объект `Menu`, который будет наполняться ответами B), **`Reader`**, **`BillPresenter`**, и снова **`menuService` как `MenuCatalogSource`** — чтобы перед заказом принудительно синхронизировать каталог.
9. Создаются **`PaymentService`**, **`CafeService`** и вызывается **`cafeService.run()`** — основной цикл меню приложения.
10. В блоке **`finally`**: **`catalogClient.close()`** (корректное завершение канала), **`reader.close()`**.

---

## 4. Цикл консоли (сервис A) — `CafeService.run()`

Выполнение по веткам внутри бесконечного цикла:

1. Печатается рамка и **нумерованный список действий** (объекты **`Action`**: показать меню, заказ, чек, оплата) и пункт **0 — выход**.
2. **`reader.readInt("Выберите действие:")`** — если ввод не число, **`ValidationException`** из **`Reader`**, сообщение пользователю, **`continue`** к следующей итерации.
3. Если выбор **0**: при ненулевом счёте заказа — предупреждение и **нельзя выйти**; при нуле — флаг выхода, выход из цикла.
4. Если номер вне диапазона — сообщение «неизвестная команда», **`continue`**.
5. Перед выполнением выбранного действия: **`TraceContext.set(UUID.randomUUID())`** — один **trace id на одно пользовательское действие** в этом цикле.
6. Вызывается **`actions.get(choice - 1).execute()`** — делегирование в конкретную команду (см. раздел 5).
7. Ловятся **`ValidationException`**, **`PaymentException`** — текст в консоль, без падения всего приложения.
8. В **`finally`**: **`TraceContext.clear()`** — чтобы следующий выбор меню получил новый trace при необходимости.

---

## 5. Действия пользователя (что вызывается по цепочке)

### 5.1. «Показать меню» — `ShowMenuAction.execute()`

1. **`menuService.displayMenu()`**.
2. Внутри **`MenuService`**: сначала **`ensureCatalogLoaded()`** → делегирование в **`MenuCatalogGrpcClient.fetchAndApplyTo(menu, dishFactory)`**.
3. **`TraceContext.getOrCreate()`** — берётся trace, заданный в **`CafeService`** для этого действия (или создаётся, если вдруг пусто).
4. В консоль A: **`[core-service] [traceId=…] запрос GetMenu к reference-service`**.
5. Через **`TraceClientInterceptor.start(...)`** в исходящие **metadata** кладётся ключ **`TraceMetadata.TRACE_ID`** (= **`x-trace-id`**), значение — текущий trace.
6. Выполняется **`stub.withDeadlineAfter(5, SECONDS).getMenu(GetMenuRequest.getDefaultInstance())`** — сетевой вызов к B.
7. На стороне B **`TraceServerInterceptor`** читает **`x-trace-id`** из metadata, кладёт в **`Context`**, затем вызывается **`MenuCatalogGrpcServiceImpl.getMenu`**.
8. В B в лог: **`[reference-service] [traceId=…] GetMenu`**.
9. Из объекта **`Menu`** сервиса B собирается **`GetMenuResponse`** (список **`DishItem`**), ответ уходит клиенту, вызов **`onCompleted`**.
10. В A: у **`Menu`** вызывается **`clear()`**, затем через **`MenuUnitOfWork`** в меню добавляются блюда из ответа (те же поля, что были в proto).
11. В консоль A: строка об успешной загрузке и числе позиций.
12. **`menu.showMenu()`** — печать нумерованного списка в консоль.

**Если B недоступен:** ловится **`StatusRuntimeException`**; для кодов **`UNAVAILABLE`** и **`DEADLINE_EXCEEDED`** печатается **понятное сообщение** (проверить, что reference-service запущен и адрес верный), метод возвращает **`false`**, **`displayMenu`** не печатает список блюд — **приложение не падает**.

### 5.2. «Сделать заказ» — `MakeOrderAction` → `OrderService.makeOrder()`

1. Снова **`catalogSource.ensureCatalogLoaded()`** — повторный **`GetMenu`**, если меню ещё не синхронизировано или нужно обновить (в текущей логике это гарантирует актуальный список перед выбором номеров).
2. Если каталог не подтянулся (**`false`**), **`makeOrder`** тихо **выходит** — пользователь уже увидел сообщение об недоступности B при попытке загрузки.
3. **`OrderSelectionFlow.run(order, menu)`**:
   - цикл: **`reader.readInt`** для номера блюда;
   - **`menu.getDishByNumber(n)`** — если номер неверный, **`ValidationException`** из домена **`Menu`**, ловится в **`CafeService`**;
   - вопрос «добавить ещё»; при **0** цикл заканчивается.

### 5.3. «Показать чек» — `ShowBillAction` → `OrderService.showBill()` → `BillPresenter.present(order)`

1. Обход **`order.getOrderedDishes()`**: для каждой пары «блюдо × количество» считается сумма по **`dish.getPrice()`**.
2. Печать итоговой строки и разделителя.

### 5.4. «Оплатить заказ» — `PayOrderAction`

1. Если сумма заказа **0** — сообщение, что нечего оплачивать, выход.
2. **`paymentService.payService(bill)`** — вывод способов оплаты, **`readInt`** выбора, вызов **`pay`** у стратегии (**наличные / карта**).
3. При успехе: **`orderService.clearOrder()`**, сообщение об успешной оплате.

---

## 6. Trace ID (сквозная идентификация запроса)

1. **A** перед действием пользователя задаёт UUID в **`TraceContext`** (потокобезопасный **`ThreadLocal`**).
2. **Клиентский перехватчик** кладёт это значение в gRPC-metadata как **`x-trace-id`**.
3. **Серверный перехватчик B** читает заголовок, кладёт в **`Context`**, реализация RPC читает его и пишет в **`System.out`** с префиксом **`[reference-service]`**.
4. **A** после вызова RPC тоже пишет в консоль с префиксом **`[core-service]`** и **тем же** `traceId`, если он был установлен до вызова — по логам двух процессов можно сопоставить одну операцию.

---

## 7. Сборка и тесты

1. Из корня **`lr4`**: **`mvn clean test`** — собираются все модули, в **`common`** гоняется **`MenuCsvLoaderTest`**, в **`core-service`** — минимальный **`AppTest`**.

---

## 8. Краткая схема потока данных

```
[Пользователь, консоль A]
        │
        ▼
   CafeService → Action
        │
        ├─► MenuService / OrderService
        │         │
        │         └──► MenuCatalogGrpcClient ──gRPC GetMenu──► reference-service (B)
        │                                                      │
        │                                                      └──► Menu (из CSV)
        │
        └──► Order, PaymentService (только внутри A)
```

Итог: **B** владеет **источником правды по каталогу**; **A** хранит **копию меню в RAM** после каждого успешного **`GetMenu`** и на её основе ведёт заказ и расчёт суммы; при падении сети или остановке B **A остаётся живым** и сообщает об этом в консоль.
# MINS-LR4
