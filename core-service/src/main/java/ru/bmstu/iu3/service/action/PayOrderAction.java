package ru.bmstu.iu3.service.action;

import ru.bmstu.iu3.service.OrderService;
import ru.bmstu.iu3.service.PaymentService;

public class PayOrderAction implements Action {
    private final OrderService orderService;
    private final PaymentService paymentService;

    public PayOrderAction(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @Override
    public String description() {
        return "Оплатить заказ";
    }

    @Override
    public void execute() {
        int bill = orderService.getBill();
        if (bill == 0) {
            System.out.println("У вас нет активного заказа для оплаты.");
            return;
        }
        paymentService.payService(bill);
        orderService.clearOrder();
        System.out.println("Заказ успешно оплачен.");
    }
}
