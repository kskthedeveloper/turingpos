package com.turing.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemCategory {
    int id;
    String name;

    public ItemCategory() {
    }

    public ItemCategory(int id) {
        this.id = id;
    }

    public ItemCategory(String name) {
        this.name = name;
    }

    public ItemCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ItemCategory parseItemCategory(ResultSet rt) throws SQLException {
        ItemCategory itemCategory = new ItemCategory();

        itemCategory.setId(rt.getInt("id"));
        itemCategory.setName(rt.getString("name"));

        return itemCategory;
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
