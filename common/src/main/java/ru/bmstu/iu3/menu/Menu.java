package ru.bmstu.iu3.menu;

import ru.bmstu.iu3.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu implements MenuRepository {
    private final List<Dish> menuItems = new ArrayList<>();

    @Override
    public void addDish(Dish dish) {
        menuItems.add(dish);
    }

    @Override
    public void removeDish(Dish dish) {
        menuItems.remove(dish);
    }

    /**
     * Очищает каталог (перед повторной загрузкой из справочника).
     */
    public void clear() {
        menuItems.clear();
    }

    public int size() {
        return menuItems.size();
    }

    public List<Dish> getDishList() {
        return Collections.unmodifiableList(new ArrayList<>(menuItems));
    }

    @Override
    public void showMenu() {
        for (int i = 0; i < menuItems.size(); i++) {
            Dish dish = menuItems.get(i);
            System.out.println((i + 1) + ". " + dish.getName() + " --------------------------------- " + dish.getPrice() + " руб.\n Описание: " + dish.getDescription());
        }
    }

    @Override
    public Dish getDishByNumber(int number) {
        if (number < 1 || number > menuItems.size()) {
            throw new ValidationException("Неверный номер блюда. Пожалуйста, выберите номер от 1 до " + menuItems.size() + ".");
        }
        return menuItems.get(number - 1);
    }
}
