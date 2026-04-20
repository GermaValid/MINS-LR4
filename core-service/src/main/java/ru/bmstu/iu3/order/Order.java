package ru.bmstu.iu3.order;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ru.bmstu.iu3.menu.Dish;

public class Order implements OrderManager {

    private final Map<Dish, Integer> orderedDishes = new HashMap<>();

    @Override
    public Map<Dish, Integer> getOrderedDishes() {
        return Collections.unmodifiableMap(new HashMap<>(orderedDishes));
    }

    @Override
    public void addDish(Dish dish) {
        orderedDishes.put(dish, orderedDishes.getOrDefault(dish, 0) + 1);
    }

    @Override
    public void removeDish(Dish dish) {
        orderedDishes.put(dish, orderedDishes.getOrDefault(dish, 0) - 1);
        if (orderedDishes.get(dish) <= 0) {
            orderedDishes.remove(dish);
        }
    }

    @Override
    public int getPrice() {
        int totalPrice = 0;
        for (Map.Entry<Dish, Integer> entry : orderedDishes.entrySet()) {
            Dish dish = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += dish.getPrice() * quantity;
        }
        return totalPrice;
    }

    @Override
    public void clear() {
        orderedDishes.clear();
    }
}
