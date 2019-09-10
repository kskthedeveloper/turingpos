package com.turing.model;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Sale extends Transaction {

    public Sale() {
    }

    public Sale(int id) {
        this.id = id;
    }

    public Sale(List<TransactionItem> itemList, Date date, User user) {
        this.itemList = itemList;
        this.date = date;
        this.user = user;
    }

    public Sale(int id, List<TransactionItem> itemList, Date date, User user) {
        this.id = id;
        this.itemList = itemList;
        this.date = date;
        this.user = user;
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

    private String printItemList(List<TransactionItem> items) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TransactionItem item: items) {
            stringBuilder.append("{" + item.getId() + ", " + item.getName() + ", " + item.getQuantity() + "}, ");
        }
        return stringBuilder.toString();
    }
}
