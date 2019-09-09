package com.turing.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransitionItem extends Item {
    int quantity;

    public TransitionItem() {
    }

    public TransitionItem(int id) {
        super(id);
    }

    public TransitionItem(int id, int quantity) {
        super(id);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static TransitionItem parseTransitionItem(ResultSet rt) throws SQLException {
        TransitionItem transitionItem = new TransitionItem();
        transitionItem.setId(rt.getInt("id"));
        transitionItem.setName(rt.getString("name"));
        transitionItem.setPrice(rt.getInt("price"));
        transitionItem.setStockQuantity(rt.getInt("stock_quantity"));
        transitionItem.setQuantity(rt.getInt("quantity"));

        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(rt.getInt("item_category_id"));
        itemCategory.setName(rt.getString("item_category_name"));

        transitionItem.setItemCategory(itemCategory);

        return transitionItem;
    }

    @Override
    public String toString() {
        return "TransitionItem{" +
                "quantity=" + quantity +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", itemCategory=" + itemCategory.getId() +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
