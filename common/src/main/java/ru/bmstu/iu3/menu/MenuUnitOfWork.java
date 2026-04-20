package ru.bmstu.iu3.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Отложенная пакетная фиксация новых блюд в меню: накапливаем через {@link #registerNew},
 * затем одним {@link #commit()} отправляем в репозиторий или {@link #rollback()} сбрасываем.
 */
public class MenuUnitOfWork {

    private final MenuRepository repository;
    private final DishFactory dishFactory;
    private final List<Dish> newDishes = new ArrayList<>();

    public MenuUnitOfWork(MenuRepository repository, DishFactory dishFactory) {
        this.repository = repository;
        this.dishFactory = dishFactory;
    }

    public void registerNew(String name, int price, String description) {
        newDishes.add(dishFactory.create(name, price, description));
    }

    public void commit() {
        for (Dish dish : newDishes) {
            repository.addDish(dish);
        }
        newDishes.clear();
    }

    public void rollback() {
        newDishes.clear();
    }

    public boolean hasPending() {
        return !newDishes.isEmpty();
    }
}
