package com.turing.model;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    int id;
    Date date;
    User user;
    List<TransitionItem> itemList;

    public Sale() {
    }

    public Sale(int id) {
        this.id = id;
    }

    public Sale(List<TransitionItem> itemList, Date date, User user) {
        this.itemList = itemList;
        this.date = date;
        this.user = user;
    }

    public Sale(int id, List<TransitionItem> itemList, Date date, User user) {
        this.id = id;
        this.itemList = itemList;
        this.date = date;
        this.user = user;
    }

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

    public List<TransitionItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TransitionItem> itemList) {
        this.itemList = itemList;
    }

    public static Sale parseSale(ResultSet resultSet) throws SQLException {
        Sale sale = new Sale();
        sale.setId(resultSet.getInt("id"));
        sale.setDate(resultSet.getDate("date"));

        User user = new User();
        user.setId(resultSet.getInt(("user_id")));
        user.setName(resultSet.getString("name"));
        user.setUserType(UserType.fromString(resultSet.getString("usertype")));

        sale.setUser(user);

        return sale;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", itemList=[" + printItemList(itemList) + "]" +
                ", date=" + date +
                ", user=" + user.getId() +
                '}';
    }

    private String printItemList(List<TransitionItem> items) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TransitionItem item: items) {
            stringBuilder.append("{" + item.getId() + ", " + item.getName() + ", " + item.getQuantity() + "}, ");
        }
        return stringBuilder.toString();
    }
}
