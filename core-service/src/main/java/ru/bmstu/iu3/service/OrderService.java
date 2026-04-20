package ru.bmstu.iu3.service;

import ru.bmstu.iu3.menu.MenuRepository;
import ru.bmstu.iu3.order.OrderManager;

public class OrderService {

    private final OrderManager order;
    private final MenuRepository menu;
    private final OrderSelectionFlow selectionFlow;
    private final BillPresenter billPresenter;
    private final MenuCatalogSource catalogSource;

    public OrderService(
            OrderManager order,
            MenuRepository menu,
            InputReader reader,
            BillPresenter billPresenter,
            MenuCatalogSource catalogSource) {
        this.order = order;
        this.menu = menu;
        this.selectionFlow = new OrderSelectionFlow(reader);
        this.billPresenter = billPresenter;
        this.catalogSource = catalogSource;
    }

    public void makeOrder() {
        if (!catalogSource.ensureCatalogLoaded()) {
            return;
        }
        selectionFlow.run(order, menu);
    }

    public int getBill() {
        return order.getPrice();
    }

    public void showBill() {
        billPresenter.present(order);
    }

    public void clearOrder() {
        order.clear();
    }
}
