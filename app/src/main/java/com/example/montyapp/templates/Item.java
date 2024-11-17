package com.example.montyapp.templates;

public class Item {
    private String title; // Название
    private String price; // Цена

    // Конструктор
    public Item(String title, String price) {
        this.title = title;
        this.price = price;
    }

    // Геттеры
    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }
}