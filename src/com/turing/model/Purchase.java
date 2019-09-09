package com.turing.model;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Purchase {
    int id;
    Date date;
    User user;
    List<TransitionItem> itemList;

    public Purchase() {
    }

    public Purchase(int id) {
        this.id = id;
    }

    public Purchase(List<TransitionItem> itemList, Date date, User user) {
        this.itemList = itemList;
        this.date = date;
        this.user = user;
    }

    public Purchase(int id, List<TransitionItem> itemList, Date date, User user) {
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

    public static Purchase parsePurchase(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(("user_id")));
        user.setName(resultSet.getString("name"));
        user.setUserType(UserType.fromString(resultSet.getString("usertype")));

        Purchase purchase = new Purchase();
        purchase.setId(resultSet.getInt("id"));
        purchase.setDate(resultSet.getDate("date"));

        purchase.setUser(user);

        return purchase;
    }

    @Override
    public String toString() {
        return "Purchase{" +
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
