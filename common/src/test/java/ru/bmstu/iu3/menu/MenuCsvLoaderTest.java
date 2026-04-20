package ru.bmstu.iu3.menu;

import org.junit.Test;
import ru.bmstu.iu3.exception.ValidationException;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MenuCsvLoaderTest {

    @Test
    public void loadsThreeColumns() throws Exception {
        String csv = "Название блюда,стоимость,описание\nЧай,50,Зелёный\n";
        List<Dish> captured = new ArrayList<>();
        MenuRepository repo = new MenuRepository() {
            @Override
            public void addDish(Dish dish) {
                captured.add(dish);
            }

            @Override
            public void removeDish(Dish dish) {
            }

            @Override
            public void showMenu() {
            }

            @Override
            public Dish getDishByNumber(int number) {
                return null;
            }
        };
        MenuUnitOfWork uow = new MenuUnitOfWork(repo, new DefaultDishFactory());
        MenuCsvLoader.loadInto(new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8)), uow);
        uow.commit();
        assertEquals(1, captured.size());
        assertEquals("Чай", captured.get(0).getName());
        assertEquals(50, captured.get(0).getPrice());
        assertEquals("Зелёный", captured.get(0).getDescription());
    }

    @Test
    public void rollbackOnBadRow() throws Exception {
        String csv = "Название блюда,стоимость,описание\nЧай,50,Ок\nПлохо,нечисло,\n";
        List<Dish> captured = new ArrayList<>();
        MenuRepository repo = new MenuRepository() {
            @Override
            public void addDish(Dish dish) {
                captured.add(dish);
            }

            @Override
            public void removeDish(Dish dish) {
            }

            @Override
            public void showMenu() {
            }

            @Override
            public Dish getDishByNumber(int number) {
                return null;
            }
        };
        MenuUnitOfWork uow = new MenuUnitOfWork(repo, new DefaultDishFactory());
        try {
            MenuCsvLoader.loadInto(new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8)), uow);
        } catch (ValidationException e) {
            assertTrue(e.getMessage().contains("стоимость"));
        }
        uow.commit();
        assertTrue(captured.isEmpty());
    }
}
