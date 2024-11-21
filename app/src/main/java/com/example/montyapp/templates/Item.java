package com.example.montyapp.templates;

public class Item {
    private String title; // Название
    private double price; // Цена

    // Конструктор
    public Item(String title, double price) {
        this.title = title;
        this.price = price;
    }

    // Геттеры
    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }
}