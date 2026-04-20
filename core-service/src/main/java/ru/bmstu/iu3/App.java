package ru.bmstu.iu3;

import ru.bmstu.iu3.core.grpc.MenuCatalogGrpcClient;
import ru.bmstu.iu3.order.Order;
import ru.bmstu.iu3.payment.CardPayment;
import ru.bmstu.iu3.payment.CashPayment;
import ru.bmstu.iu3.payment.Payment;
import ru.bmstu.iu3.service.BillPresenter;
import ru.bmstu.iu3.service.CafeService;
import ru.bmstu.iu3.service.MenuService;
import ru.bmstu.iu3.service.OrderService;
import ru.bmstu.iu3.service.PaymentService;
import ru.bmstu.iu3.service.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис A — консольное ядро; меню загружается из сервиса B по gRPC.
 */
public class App {

    public static void main(String[] args) {
        String host = System.getenv().getOrDefault("REFERENCE_GRPC_HOST", "127.0.0.1");
        int port = Integer.parseInt(System.getenv().getOrDefault("REFERENCE_GRPC_PORT", "50051"));

        Reader reader = new Reader();
        MenuCatalogGrpcClient catalogClient = new MenuCatalogGrpcClient(host, port);
        try {
            Order order = new Order();
            List<Payment> paiments = new ArrayList<>();
            paiments.add(new CardPayment());
            paiments.add(new CashPayment());
            MenuService menuService = new MenuService(catalogClient);
            OrderService orderService = new OrderService(order, menuService.getMenu(), reader, new BillPresenter(), menuService);
            PaymentService paimentService = new PaymentService(paiments, reader);
            CafeService cafeService = new CafeService(menuService, orderService, paimentService, reader);
            cafeService.run();
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка. Обратитесь к разработчику.");
            e.printStackTrace();
        } finally {
            try {
                catalogClient.close();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            reader.close();
        }
    }
}
