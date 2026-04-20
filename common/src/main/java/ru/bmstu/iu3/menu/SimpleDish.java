package ru.bmstu.iu3.menu;

public final class SimpleDish implements Dish {
    private final String name;
    private final int price;
    private final String description;

    public SimpleDish(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
