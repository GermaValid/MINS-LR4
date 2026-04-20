package ru.bmstu.iu3.menu;

import ru.bmstu.iu3.exception.ValidationException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Загрузка меню справочника из classpath (используется сервисом B).
 */
public final class MenuBootstrap {

    private MenuBootstrap() {
    }

    public static Menu loadFromClasspath() {
        Menu menu = new Menu();
        DishFactory dishFactory = new DefaultDishFactory();
        MenuUnitOfWork uow = new MenuUnitOfWork(menu, dishFactory);
        try (InputStream in = MenuBootstrap.class.getResourceAsStream("/data/menu.csv")) {
            if (in != null) {
                try {
                    MenuCsvLoader.loadInto(in, uow);
                } catch (ValidationException e) {
                    seedDefaultMenu(uow);
                }
            } else {
                seedDefaultMenu(uow);
            }
        } catch (IOException e) {
            uow.rollback();
            seedDefaultMenu(uow);
        }
        uow.commit();
        return menu;
    }

    private static void seedDefaultMenu(MenuUnitOfWork uow) {
        uow.registerNew("Кофе", 100, "Горячий и ароматный кофе");
        uow.registerNew("Чай", 50, "Прохладный чай");
        uow.registerNew("Пирожное", 200, "Вкусное пирожное");
    }
}
