package com.turing.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionItem extends Item {
    int quantity;

    public TransactionItem() {
    }

    public TransactionItem(int id) {
        super(id);
    }

    public TransactionItem(int id, int quantity) {
        super(id);
        this.quantity = quantity;
    }

    public TransactionItem(Item item, int quantity) {
        this.id = item.getId();
        this.itemCode = item.getItemCode();
        this.itemCategory = item.getItemCategory();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static TransactionItem parseTransitionItem(ResultSet rt) throws SQLException {
        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setId(rt.getInt("id"));
        transactionItem.setItemCode(rt.getString("itemCode"));
        transactionItem.setName(rt.getString("name"));
        transactionItem.setPrice(rt.getInt("price"));
        transactionItem.setStockQuantity(rt.getInt("stock_quantity"));
        transactionItem.setQuantity(rt.getInt("quantity"));

        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(rt.getInt("item_category_id"));
        itemCategory.setName(rt.getString("item_category_name"));

        transactionItem.setItemCategory(itemCategory);

        return transactionItem;
    }

    @Override
    public String toString() {
        return "TransactionItem{" +
                "quantity=" + quantity +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", itemCategory=" + itemCategory.getId() +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
