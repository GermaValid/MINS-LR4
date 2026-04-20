package ru.bmstu.iu3.service;

import ru.bmstu.iu3.menu.MenuRepository;
import ru.bmstu.iu3.order.OrderManager;

/**
 * Сценарий ввода позиций заказа с консоли. OrderService остаётся точкой входа к домену заказа и чеку.
 */
final class OrderSelectionFlow {

    private final InputReader reader;

    OrderSelectionFlow(InputReader reader) {
        this.reader = reader;
    }

    void run(OrderManager order, MenuRepository menu) {
        boolean adding = true;
        System.out.println("------------------------------------------");
        while (adding) {
            order.addDish(menu.getDishByNumber(reader.readInt("Выберите номер блюда для добавления в заказ:")));
            System.out.println();
            if (reader.readInt("Хотите добавить еще блюдо? 1 - Да, 0 - Нет") == 0) {
                adding = false;
            }
        }
    }
}
