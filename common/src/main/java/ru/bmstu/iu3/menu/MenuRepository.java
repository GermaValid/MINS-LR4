package ru.bmstu.iu3.menu;

public interface MenuRepository {
    void addDish(Dish dish);

    void removeDish(Dish dish);

    void showMenu();

    Dish getDishByNumber(int number);
}
