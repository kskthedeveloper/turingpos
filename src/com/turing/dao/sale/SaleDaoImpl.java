package com.turing.dao.sale;

import com.turing.dao.Dao;
import com.turing.dao.item.ItemDao;
import com.turing.dao.item.ItemDaoImpl;
import com.turing.dao.itemcategory.ItemCategoryDao;
import com.turing.dao.itemcategory.ItemCategoryDaoImpl;
import com.turing.dao.stock.StockDao;
import com.turing.dao.user.UserDao;
import com.turing.dao.user.UserDaoImpl;
import com.turing.model.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SaleDaoImpl extends Dao implements SaleDao {

    @Override
    public void insert(Sale sale) {
        int id = 0;
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO sale(date, user_id) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, sale.getDate());
            st.setInt(2, sale.getUser().getId());
            st.executeUpdate();

            ResultSet rs=st.getGeneratedKeys();

            if(rs.next()){
                id=rs.getInt(1);
            }

            for(TransitionItem item: sale.getItemList()) {
                insertSaleItem(id, item);
                reduceStockQuantity(item);
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSaleItem(int saleId, TransitionItem item) {
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO item_sale(sale_id, item_id, quantity) VALUES(?,?,?)");
            st.setInt(1, saleId);
            st.setInt(2, item.getId());
            st.setInt(3, item.getQuantity());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reduceStockQuantity(TransitionItem item) {
        try {
            PreparedStatement st = this.connection.prepareStatement(
                "UPDATE item\n" +
                "SET item.stock_quantity = item.stock_quantity - ?\n" +
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
    public void update(Sale sale) {

    }

    @Override
    public void delete(Sale sale) {

    }

    @Override
    public Sale get(Sale sale) {
        Sale sale1 = null;
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                    "FROM sale\n" +
                    "JOIN user ON sale.user_id=user.id\n" +
                    "WHERE sale.id=" + sale.getId());

            if(resultSet.next()) {
                sale1 = Sale.parseSale(resultSet);
            }

            sale1 = populateSale(sale1, st);

            return sale1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sale1;
    }

    @Override
    public List<Sale> getAll() {
        List<Sale> sales = new ArrayList<>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                    "FROM sale\n" +
                    "JOIN user ON sale.user_id=user.id");

            while (resultSet.next()) {
                sales.add(Sale.parseSale(resultSet));
            }

            sales = populateSaleList(sales, st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    @Override
    public List<Sale> getSalesByCashier(int cashierId) {
        List<Sale> sales = new ArrayList<>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                            "FROM sale\n" +
                            "JOIN user ON sale.user_id=user.id\n"+
                            "WHERE user.id=" + cashierId);

            while (resultSet.next()) {
                sales.add(Sale.parseSale(resultSet));
            }

            sales = populateSaleList(sales, st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    @Override
    public List<Sale> getSalesByDate(Date startDate, Date endDate) {
        List<Sale> sales = new ArrayList<>();
        try {
            PreparedStatement st = this.connection.prepareStatement(
                    "SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                            "FROM sale\n" +
                            "JOIN user ON sale.user_id=user.id\n"+
                            "WHERE sale.date BETWEEN ? AND ?"
            );

            st.setDate(1, startDate);
            st.setDate(2, endDate);

            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                sales.add(Sale.parseSale(resultSet));
            }

            sales = populateSaleList(sales, st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    @Override
    public List<Sale> getSalesByCashierAndDate(int cashierId, Date startDate, Date endDate) {
        List<Sale> sales = new ArrayList<>();
        try {
            PreparedStatement st = this.connection.prepareStatement(
                    "SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                            "FROM sale\n" +
                            "JOIN user ON sale.user_id=user.id\n"+
                            "WHERE sale.date BETWEEN ? AND ?\n" +
                            "AND user.id=?"
            );

            st.setDate(1, startDate);
            st.setDate(2, endDate);
            st.setInt(3, cashierId);

            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                sales.add(Sale.parseSale(resultSet));
            }

            sales = populateSaleList(sales, st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    @Override
    public List<Sale> getSalesByMonth(Month month, int year) {
        LocalDate date = LocalDate.of(year, month, 1);
        Date startDate = Date.valueOf(date);
        Date endDate = Date.valueOf(date.withDayOfMonth(date.lengthOfMonth()));
        return getSalesByDate(startDate, endDate);
    }


    private Sale populateSale(Sale sale, Statement st) throws SQLException {
        List<TransitionItem> items = new ArrayList<>();

        ResultSet resultSet = st.executeQuery(
                "SELECT item.id, item.name, item.price, item.stock_quantity, " +
                        "item_sale.quantity, item_category.id as item_category_id, " +
                        "item_category.name as item_category_name\n" +
                        "FROM item_sale\n" +
                        "JOIN item ON item_sale.item_id = item.id\n" +
                        "JOIN sale ON item_sale.sale_id = sale.id\n" +
                        "JOIN user ON sale.user_id = user.id\n" +
                        "JOIN item_category ON item.itemcategory_id = item_category.id\n" +
                        "WHERE sale.id=" + sale.getId());

        while (resultSet.next()) {
            items.add(TransitionItem.parseTransitionItem(resultSet));
        }

        sale.setItemList(items);

        return sale;
    }

    private List<Sale> populateSaleList(List<Sale> sales,  Statement st) throws SQLException {
        List<Sale> populatedSaleList = new ArrayList<>();

        for (Sale sale: sales) {
            populatedSaleList.add(populateSale(sale, st));
        }

        return populatedSaleList;
    }



    // Testing
    public static void main(String[] args) {

        SaleDao saleDao = new SaleDaoImpl();

        testGetByMonth(saleDao);
    }

    private static void testInsert(SaleDao saleDao) {
        TransitionItem item = new TransitionItem(2);
        item.setQuantity(2);

        TransitionItem item2 = new TransitionItem(3);
        item2.setQuantity(3);

        List<TransitionItem> items = new ArrayList<>();
        items.add(item);
        items.add(item2);
        User user = new User(2);

        Sale sale = new Sale();
        sale.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        sale.setUser(user);
        sale.setItemList(items);

        saleDao.insert(sale);
    }

    private static void testGet(SaleDao saleDao) {
        Sale sale = new Sale(8);
        System.out.println(saleDao.get(sale));

    }

    private static void testGetAll(SaleDao saleDao) {
        for (Sale sale: saleDao.getAll()) {
            System.out.println(sale);
        }
    }

    private static void testGetByDate(SaleDao saleDao) {
        LocalDate date = LocalDate.now();
        Date startDate = Date.valueOf(date);
        Date endDate = Date.valueOf(date);
        for (Sale sale: saleDao.getSalesByDate(startDate, endDate)) {
            System.out.println(sale);
        }
    }

    private static void testGetByMonth(SaleDao saleDao) {
        for (Sale sale: saleDao.getSalesByMonth(Month.AUGUST, 2019)) {
            System.out.println(sale);
        }
    }

    private static void testGetCashierAndDate(SaleDao saleDao) {
        LocalDate date = LocalDate.now();
        Date startDate = Date.valueOf(LocalDate.of(2019, 8, 7));
        Date endDate = Date.valueOf(LocalDate.of(2019, 8, 7));

        for (Sale sale: saleDao.getSalesByCashierAndDate(2, startDate, endDate)) {
            System.out.println(sale);
        }
    }

}
