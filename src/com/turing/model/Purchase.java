package com.turing.model;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Purchase extends Transaction {

    public Purchase() {
    }

    public Purchase(int id) {
        this.id = id;
    }

    public Purchase(List<TransactionItem> itemList, Date date, User user) {
        this.itemList = itemList;
        this.date = date;
        this.user = user;
    }

    public Purchase(int id, List<TransactionItem> itemList, Date date, User user) {
        this.id = id;
        this.itemList = itemList;
        this.date = date;
        this.user = user;
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

    private String printItemList(List<TransactionItem> items) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TransactionItem item: items) {
            stringBuilder.append("{" + item.getId() + ", " + item.getName() + ", " + item.getQuantity() + "}, ");
        }
        return stringBuilder.toString();
    }
}
