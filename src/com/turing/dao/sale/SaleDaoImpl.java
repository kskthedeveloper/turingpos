package com.turing.dao.sale;

import com.turing.dao.Dao;
import com.turing.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SaleDaoImpl extends Dao implements SaleDao {

    @Override
    public void insert(Sale sale) {
        int id = 0;
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO sale(date, user_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, sale.getDate());
            st.setInt(2, sale.getUser().getId());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            for (TransactionItem item : sale.getItemList()) {
                insertSaleItem(id, item);
                reduceStockQuantity(item);
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSaleItem(int saleId, TransactionItem item) {
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

    public void reduceStockQuantity(TransactionItem item) {
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
    public Transaction get(Sale sale) {
        Transaction sale1 = null;
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                    "FROM sale\n" +
                    "JOIN user ON sale.user_id=user.id\n" +
                    "WHERE sale.id=" + sale.getId());

            if (resultSet.next()) {
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
    public List<Transaction> getAll() {
        List<Transaction> sales = new ArrayList<>();
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
    public List<Transaction> getSalesByCashier(int cashierId) {
        List<Transaction> sales = new ArrayList<>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                            "FROM sale\n" +
                            "JOIN user ON sale.user_id=user.id\n" +
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
    public List<Transaction> getSalesByDate(Date startDate, Date endDate) {
        List<Transaction> sales = new ArrayList<>();
        try {
            PreparedStatement st = this.connection.prepareStatement(
                    "SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                            "FROM sale\n" +
                            "JOIN user ON sale.user_id=user.id\n" +
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
    public List<Transaction> getSalesByCashierAndDate(int cashierId, Date startDate, Date endDate) {
        List<Transaction> sales = new ArrayList<>();
        try {
            PreparedStatement st = this.connection.prepareStatement(
                    "SELECT sale.id, sale.date, user.id as user_id, user.name, user.usertype\n" +
                            "FROM sale\n" +
                            "JOIN user ON sale.user_id=user.id\n" +
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
    public List<Transaction> getSalesByMonth(Month month, int year) {
        LocalDate date = LocalDate.of(year, month, 1);
        Date startDate = Date.valueOf(date);
        Date endDate = Date.valueOf(date.withDayOfMonth(date.lengthOfMonth()));
        return getSalesByDate(startDate, endDate);
    }

    @Override
    public List<TransactionItem> getSalesReportByMonth(Month month, int year) {
        LocalDate date = LocalDate.of(year, month, 1);
        Date startDate = Date.valueOf(date);
        Date endDate = Date.valueOf(date.withDayOfMonth(date.lengthOfMonth()));
        try {
            return getSaleReportByDate(startDate, endDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TransactionItem> getSaleReportByDate(Date startDate, Date endDate) throws SQLException {
        List<TransactionItem> items = new ArrayList<>();

        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT item_sale.item_id as id, item.name, item.itemCode, item.price, item.stock_quantity, item.itemcategory_id as item_category_id,item_category.name as item_category_name, SUM(item_sale.quantity) as quantity\n" +
                        "FROM item_sale\n" +
                        "JOIN item ON item.id = item_sale.item_id\n" +
                        "JOIN sale ON sale.id=item_sale.sale_id\n" +
                        "JOIN item_category ON item_category.id=item.itemcategory_id\n" +
                        "WHERE sale.date BETWEEN ? AND ? \n" +
                        "GROUP BY item_sale.item_id;"
        );

        ps.setDate(1, startDate);
        ps.setDate(2, endDate);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            items.add(TransactionItem.parseTransitionItem(resultSet));
        }

        return items;
    }

    @Override
    public List<TransactionItem> getSaleReportByCashier(User user) {
        List<TransactionItem> items = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = this.connection.prepareStatement(
                "SELECT item_sale.item_id as id, item.name, item.itemCode, item.price, item.stock_quantity, item.itemcategory_id as item_category_id,item_category.name as item_category_name, SUM(item_sale.quantity) as quantity\n" +
                        "FROM item_sale\n" +
                        "JOIN item ON item.id = item_sale.item_id\n" +
                        "JOIN sale ON sale.id=item_sale.sale_id\n" +
                        "JOIN user ON user.id=sale.user_id\n" +
                        "JOIN item_category ON item_category.id=item.itemcategory_id\n" +
                        "WHERE user.id=?\n" +
                        "GROUP BY item_sale.item_id;"
            );

            ps.setInt(1, user.getId());

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                items.add(TransactionItem.parseTransitionItem(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return items;
    }

    @Override
    public List<TransactionItem> getSaleReportByCashierAndDate(User user, Date startDate, Date endDate) {
        List<TransactionItem> items = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = this.connection.prepareStatement(
                "SELECT item_sale.item_id as id, item.name, item.itemCode, item.price, item.stock_quantity, item.itemcategory_id as item_category_id,item_category.name as item_category_name, SUM(item_sale.quantity) as quantity\n" +
                        "FROM item_sale\n" +
                        "JOIN item ON item.id = item_sale.item_id\n" +
                        "JOIN sale ON sale.id=item_sale.sale_id\n" +
                        "JOIN user ON user.id=sale.user_id\n" +
                        "JOIN item_category ON item_category.id=item.itemcategory_id\n" +
                        "WHERE sale.date BETWEEN ? AND ?\n" +
                        "AND user.id=?\n" +
                        "GROUP BY item_sale.item_id;"
            );

            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ps.setInt(3, user.getId());

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                items.add(TransactionItem.parseTransitionItem(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return items;
    }


    private Transaction populateSale(Transaction sale, Statement st) throws SQLException {
        List<TransactionItem> items = new ArrayList<>();

        ResultSet resultSet = st.executeQuery(
                "SELECT item.id, item.itemCode, item.name, item.price, item.stock_quantity, " +
                        "item_sale.quantity, item_category.id as item_category_id, " +
                        "item_category.name as item_category_name\n" +
                        "FROM item_sale\n" +
                        "JOIN item ON item_sale.item_id = item.id\n" +
                        "JOIN sale ON item_sale.sale_id = sale.id\n" +
                        "JOIN user ON sale.user_id = user.id\n" +
                        "JOIN item_category ON item.itemcategory_id = item_category.id\n" +
                        "WHERE sale.id=" + sale.getId());

        while (resultSet.next()) {
            items.add(TransactionItem.parseTransitionItem(resultSet));
        }

        sale.setItemList(items);

        return sale;
    }


    private List<Transaction> populateSaleList(List<Transaction> sales, Statement st) throws SQLException {
        List<Transaction> populatedSaleList = new ArrayList<>();

        for (Transaction sale : sales) {
            populatedSaleList.add(populateSale(sale, st));
        }

        return populatedSaleList;
    }


    // Testing
    public static void main(String[] args) {

        SaleDao saleDao = new SaleDaoImpl();

        testGetSaleReportByCashier(saleDao);
    }

    private static void testInsert(SaleDao saleDao) {
        TransactionItem item = new TransactionItem(2);
        item.setQuantity(2);

        TransactionItem item2 = new TransactionItem(3);
        item2.setQuantity(3);

        List<TransactionItem> items = new ArrayList<>();
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
        for (Transaction sale : saleDao.getAll()) {
            System.out.println(sale);
        }
    }

    private static void testGetByDate(SaleDao saleDao) {
        LocalDate date = LocalDate.now();
        Date startDate = Date.valueOf("2019-07-01");
        Date endDate = Date.valueOf(date);
        for (Transaction sale : saleDao.getSalesByDate(startDate, endDate)) {
            System.out.println(sale);
        }
    }

    private static void testGetSaleReportByDate(SaleDao saleDao) {
        LocalDate date = LocalDate.now();
        Date startDate = Date.valueOf("2019-07-01");
        Date endDate = Date.valueOf(date);
        try {
            for (TransactionItem item : saleDao.getSaleReportByDate(startDate, endDate)) {
                System.out.println(item.getName() + ", " + item.getItemCategory().getName() + ", " + item.getQuantity());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testGetByMonth(SaleDao saleDao) {
        for (Transaction sale : saleDao.getSalesByMonth(Month.AUGUST, 2019)) {
            System.out.println(sale);
        }
    }

    private static void testGetCashierAndDate(SaleDao saleDao) {
        LocalDate date = LocalDate.now();
        Date startDate = Date.valueOf(LocalDate.of(2019, 8, 7));
        Date endDate = Date.valueOf(LocalDate.of(2019, 8, 7));

        for (Transaction sale : saleDao.getSalesByCashierAndDate(2, startDate, endDate)) {
            System.out.println(sale);
        }
    }

    private static void testGetSaleReportByCashier(SaleDao saleDao) {
        
        for (TransactionItem item : saleDao.getSaleReportByCashier(new User(6))) {
                System.out.println(item.getName() + ", " + item.getItemCategory().getName() + ", " + item.getQuantity());
        }
    }

}
