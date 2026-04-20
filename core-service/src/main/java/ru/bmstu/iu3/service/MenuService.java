package ru.bmstu.iu3.service;

import ru.bmstu.iu3.core.grpc.MenuCatalogGrpcClient;
import ru.bmstu.iu3.menu.DefaultDishFactory;
import ru.bmstu.iu3.menu.DishFactory;
import ru.bmstu.iu3.menu.Menu;

public class MenuService implements MenuCatalogSource {

    private final Menu menu = new Menu();
    private final DishFactory dishFactory = new DefaultDishFactory();
    private final MenuCatalogGrpcClient catalogClient;

    public MenuService(MenuCatalogGrpcClient catalogClient) {
        this.catalogClient = catalogClient;
    }

    @Override
    public boolean ensureCatalogLoaded() {
        return catalogClient.fetchAndApplyTo(menu, dishFactory);
    }

    public Menu getMenu() {
        return menu;
    }

    public void displayMenu() {
        if (!ensureCatalogLoaded()) {
            return;
        }
        menu.showMenu();
    }
}
