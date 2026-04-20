package ru.bmstu.iu3.menu;

/**
 * Блюдо в меню и заказе: абстракция для зависимостей по DIP (заказ, чек, репозиторий).
 */
public interface Dish {

    String getName();

    int getPrice();

    String getDescription();
}
