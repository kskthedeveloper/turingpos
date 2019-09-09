package com.turing.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Item {
    int id;
    String itemCode;
    String name;
    int price;
    ItemCategory itemCategory;
    int stockQuantity;

    public Item() {}

    public Item(int id) {
        this.id = id;
    }

    public Item(String name, int price, String itemCode) {
        this.name = name;
        this.price = price;
        this.itemCode = itemCode;
    }

    public Item(String name, int price, String itemCode, ItemCategory itemCategory) {
        this.name = name;
        this.price = price;
        this.itemCategory = itemCategory;
        this.itemCode = itemCode;
    }

    public Item(int id, String name, String itemCode, ItemCategory itemCategory) {
        this.id = id;
        this.name = name;
        this.itemCategory = itemCategory;
        this.itemCode = itemCode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public boolean isStockLow() {
        return this.stockQuantity < 20;
    }

    public void incraseStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    public void decreaseStock(int stockQuantity) {
        this.stockQuantity -= stockQuantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public static Item parseItem(ResultSet rt) throws SQLException {
        Item item = new Item();

        item.setId(rt.getInt("id"));
        item.setName(rt.getString("name"));
        item.setPrice(rt.getInt("price"));
        item.setItemCategory(new ItemCategory(rt.getInt("itemcategory_id")));
        item.setStockQuantity(rt.getInt("stock_quantity"));
        item.setItemCode(rt.getString("itemCode"));

        return item;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemCode=" + itemCode +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", itemCategory=" + itemCategory.getId() +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
