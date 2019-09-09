package com.turing.dao.purchase;

import com.turing.dao.Dao;
import com.turing.model.Purchase;
import com.turing.model.TransitionItem;
import com.turing.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PurchaseDaoImpl extends Dao implements PurchaseDao{
    @Override
    public void insert(Purchase purchase) {
        int id = 0;
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO purchase(date, purchase_user_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, purchase.getDate());
            st.setInt(2, purchase.getUser().getId());
            st.executeUpdate();

            ResultSet rs=st.getGeneratedKeys();

            if(rs.next()){
                id=rs.getInt(1);
            }

            for(TransitionItem item: purchase.getItemList()) {
                insertPurchaseItem(id, item);
                increaseStockQuantity(item);
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertPurchaseItem(int purchaseId, TransitionItem item) {
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO item_purchase(purchase_id, purchae_item_id, quantity) VALUES(?,?,?)");
            st.setInt(1, purchaseId);
            st.setInt(2, item.getId());
            st.setInt(3, item.getQuantity());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void increaseStockQuantity(TransitionItem item) {
        try {
            PreparedStatement st = this.connection.prepareStatement(
                    "UPDATE item\n" +
                    "SET item.stock_quantity = item.stock_quantity + ?\n" +
                    "WHERE item.id=?"
            );

            st.setInt(1, item.getQuantity());
            st.setInt(2, item.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Purchase purchase) {

    }

    @Override
    public void delete(Purchase purchase) {

    }

    @Override
    public Purchase get(Purchase purchase) {
        Purchase purchase1 = null;
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT purchase.id, purchase.date, user.id as user_id, user.name, user.usertype\n" +
                    "FROM purchase\n" +
                    "JOIN user ON purchase.purchase_user_id=user.id\n" +
                    "WHERE purchase.id=" + purchase.getId());

            if(resultSet.next()) {
                purchase1 = Purchase.parsePurchase(resultSet);
            }

            purchase1 = populatePurchase(purchase1, st);

            return purchase1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchase1;
    }

    @Override
    public List<Purchase> getAll() {
        List<Purchase> purchases = new ArrayList<>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT purchase.id, purchase.date, user.id as user_id, user.name, user.usertype\n" +
                            "FROM purchase\n" +
                            "JOIN user ON purchase.purchase_user_id=user.id");

            while (resultSet.next()) {
                purchases.add(Purchase.parsePurchase(resultSet));
            }

            purchases = populatePurchaseList(purchases, st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchases;
    }

    private Purchase populatePurchase(Purchase purchase, Statement st) throws SQLException {
        List<TransitionItem> items = new ArrayList<>();

        ResultSet resultSet = st.executeQuery(
                "SELECT item.id, item.name, item.price, item.stock_quantity, " +
                        "item_purchase.quantity, item_category.id as item_category_id, " +
                        "item_category.name as item_category_name\n" +
                        "FROM item_purchase\n" +
                        "JOIN item ON item_purchase.purchae_item_id = item.id\n" +
                        "JOIN purchase ON item_purchase.purchase_id = purchase.id\n" +
                        "JOIN user ON purchase.purchase_user_id = user.id\n" +
                        "JOIN item_category ON item.itemcategory_id = item_category.id\n" +
                        "WHERE purchase.id=" + purchase.getId());

        while (resultSet.next()) {
            items.add(TransitionItem.parseTransitionItem(resultSet));
        }

        purchase.setItemList(items);

        return purchase;
    }

    private List<Purchase> populatePurchaseList(List<Purchase> purchases,  Statement st) throws SQLException {
        List<Purchase> populatedPurchaseList = new ArrayList<>();

        for (Purchase purchase: purchases) {
            populatedPurchaseList.add(populatePurchase(purchase, st));
        }

        return populatedPurchaseList;
    }

    // Testing
    public static void main(String[] args) {
        PurchaseDao purchaseDao = new PurchaseDaoImpl();
        testGetAll(purchaseDao);
    }

    private static void testInsert(PurchaseDao purchaseDao) {
        TransitionItem item = new TransitionItem(2);
        item.setQuantity(2);

        TransitionItem item2 = new TransitionItem(3);
        item2.setQuantity(3);

        List<TransitionItem> items = new ArrayList<>();
        items.add(item);
        items.add(item2);
        User user = new User(2);

        Purchase purchase = new Purchase();
        purchase.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        purchase.setUser(user);
        purchase.setItemList(items);

        purchaseDao.insert(purchase);
    }

    private static void testGet(PurchaseDao purchaseDao) {
        Purchase purchase = new Purchase(3);
        System.out.println(purchaseDao.get(purchase));
    }

    private static void testGetAll(PurchaseDao purchaseDao) {
        for (Purchase purchase: purchaseDao.getAll()) {
            System.out.println(purchase);
        }
    }
}
