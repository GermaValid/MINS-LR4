package ru.bmstu.iu3.service.action;

import ru.bmstu.iu3.service.MenuService;

public class ShowMenuAction implements Action {
    private final MenuService menuService;

    public ShowMenuAction(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public String description() {
        return "Показать меню";
    }

    @Override
    public void execute() {
        menuService.displayMenu();
    }
}
