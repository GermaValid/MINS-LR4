package ru.bmstu.iu3.menu;

/**
 * Фабрика создания блюд (единая точка конструирования {@link Dish}).
 */
public interface DishFactory {

    Dish create(String name, int price, String description);
}
