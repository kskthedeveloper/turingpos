package com.turing.model;

// for now in Item
public class Stock {
    int id;
    Item item;
    int stockQuantiy;

    public Stock() {
    }

    public Stock(int id) {
        this.id = id;
    }

    public Stock(Item item, int stockQuantiy) {
        this.item = item;
        this.stockQuantiy = stockQuantiy;
    }

    public Stock(int id, Item item, int stockQuantiy) {
        this.id = id;
        this.item = item;
        this.stockQuantiy = stockQuantiy;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getStockQuantiy() {
        return stockQuantiy;
    }

    public void setStockQuantiy(int stockQuantiy) {
        this.stockQuantiy = stockQuantiy;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", itemcategory=" + item +
                ", stockQuantiy=" + stockQuantiy +
                '}';
    }
}
