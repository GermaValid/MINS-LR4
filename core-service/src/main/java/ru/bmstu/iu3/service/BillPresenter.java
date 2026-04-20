package ru.bmstu.iu3.service;

import java.util.Map;
import ru.bmstu.iu3.menu.Dish;
import ru.bmstu.iu3.order.OrderManager;

public class BillPresenter {

    public void present(OrderManager order) {
        int totalPrice = 0;
        for (Map.Entry<Dish, Integer> entry : order.getOrderedDishes().entrySet()) {
            Dish dish = entry.getKey();
            int quantity = entry.getValue();
            int dishTotal = dish.getPrice() * quantity;
            System.out.println(dish.getName() + " x " + quantity + " = " + dishTotal + " руб.");
            totalPrice += dishTotal;
        }
        System.out.println("Итоговая стоимость: " + totalPrice + " руб.");
        System.out.println("-=--------------------------------------=-");
    }
}
