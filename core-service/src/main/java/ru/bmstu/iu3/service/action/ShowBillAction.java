package ru.bmstu.iu3.service.action;

import ru.bmstu.iu3.service.OrderService;

public class ShowBillAction implements Action {
    private final OrderService orderService;

    public ShowBillAction(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String description() {
        return "Показать чек";
    }

    @Override
    public void execute() {
        orderService.showBill();
    }
}
