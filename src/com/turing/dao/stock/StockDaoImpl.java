package com.turing.dao.stock;

import com.turing.dao.Dao;
import com.turing.model.Item;
import com.turing.model.Stock;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StockDaoImpl extends Dao implements StockDao {

    @Override
    public void insert(Item item) {
        try {
            PreparedStatement st = this.connection.prepareStatement("UPDATE item SET stock_quantity=? WHERE id=" + item.getId());

            st.setInt(1, item.getStockQuantity());
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Testing Stock
    public static void main(String[] args) {
        StockDao stockDao = new StockDaoImpl();
        testInsert(stockDao);
    }

    private static void testInsert(StockDao stockDao) {
        Item item = new Item(2);
        item.setStockQuantity(20);
        stockDao.insert(item);
    }
}
