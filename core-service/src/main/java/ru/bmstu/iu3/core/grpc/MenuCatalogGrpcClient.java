package ru.bmstu.iu3.core.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import ru.bmstu.iu3.grpc.menu.GetMenuRequest;
import ru.bmstu.iu3.grpc.menu.GetMenuResponse;
import ru.bmstu.iu3.grpc.menu.MenuCatalogGrpc;
import ru.bmstu.iu3.menu.DishFactory;
import ru.bmstu.iu3.menu.Menu;
import ru.bmstu.iu3.menu.MenuUnitOfWork;

import java.util.concurrent.TimeUnit;

/**
 * Клиент gRPC к справочнику меню (сервис B).
 */
public final class MenuCatalogGrpcClient implements AutoCloseable {

    private final ManagedChannel channel;
    private final MenuCatalogGrpc.MenuCatalogBlockingStub stub;

    public MenuCatalogGrpcClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = MenuCatalogGrpc.newBlockingStub(channel)
                .withInterceptors(new TraceClientInterceptor());
    }

    /**
     * Загружает каталог из сервиса B в локальное меню.
     *
     * @return false если справочник недоступен (понятное сообщение уже выведено в консоль)
     */
    public boolean fetchAndApplyTo(Menu menu, DishFactory dishFactory) {
        String traceId = TraceContext.getOrCreate();
        System.out.println("[core-service] [traceId=" + traceId + "] запрос GetMenu к reference-service");
        try {
            GetMenuResponse resp = stub.withDeadlineAfter(5, TimeUnit.SECONDS)
                    .getMenu(GetMenuRequest.getDefaultInstance());
            menu.clear();
            MenuUnitOfWork uow = new MenuUnitOfWork(menu, dishFactory);
            resp.getDishesList().forEach(d ->
                    uow.registerNew(d.getName(), d.getPrice(), d.getDescription()));
            uow.commit();
            System.out.println("[core-service] [traceId=" + traceId + "] каталог успешно получен, позиций: " + resp.getDishesCount());
            return true;
        } catch (StatusRuntimeException e) {
            if (isUnavailable(e)) {
                System.out.println("[core-service] [traceId=" + traceId + "] "
                        + "Справочник меню временно недоступен. Проверьте, что сервис справочников (reference-service) "
                        + "запущен и доступен по адресу " + channel.authority() + ".");
                return false;
            }
            System.out.println("[core-service] [traceId=" + traceId + "] Ошибка gRPC: " + e.getStatus());
            return false;
        }
    }

    private static boolean isUnavailable(StatusRuntimeException e) {
        Status.Code code = e.getStatus().getCode();
        return code == Status.Code.UNAVAILABLE
                || code == Status.Code.DEADLINE_EXCEEDED;
    }

    @Override
    public void close() throws InterruptedException {
        channel.shutdown();
        channel.awaitTermination(5, TimeUnit.SECONDS);
    }
}
