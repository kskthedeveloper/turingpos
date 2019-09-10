package com.turing.model;

import java.sql.Date;
import java.util.List;

public class Transaction {
    int id;
    Date date;
    User user;
    List<TransactionItem> itemList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TransactionItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TransactionItem> itemList) {
        this.itemList = itemList;
    }
}
