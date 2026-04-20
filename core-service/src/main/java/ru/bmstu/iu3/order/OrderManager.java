package ru.bmstu.iu3.order;

import java.util.Map;
import ru.bmstu.iu3.menu.Dish;

public interface OrderManager {
    Map<Dish, Integer> getOrderedDishes();

    void addDish(Dish dish);

    void removeDish(Dish dish);

    int getPrice();

    void clear();
}
