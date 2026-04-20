package ru.bmstu.iu3.menu;

import ru.bmstu.iu3.exception.ValidationException;

public class DefaultDishFactory implements DishFactory {

    @Override
    public Dish create(String name, int price, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Название блюда не может быть пустым.");
        }
        if (price < 0) {
            throw new ValidationException("Стоимость не может быть отрицательной.");
        }
        String desc = description == null ? "" : description;
        return new SimpleDish(name.trim(), price, desc);
    }
}
