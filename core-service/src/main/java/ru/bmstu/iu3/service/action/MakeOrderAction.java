package ru.bmstu.iu3.service.action;

import ru.bmstu.iu3.service.OrderService;

public class MakeOrderAction implements Action {
    private final OrderService orderService;

    public MakeOrderAction(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String description() {
        return "Сделать заказ";
    }

    @Override
    public void execute() {
        orderService.makeOrder();
    }
}
