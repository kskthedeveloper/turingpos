package com.turing.dao.item;

import com.turing.dao.Dao;
import com.turing.dao.itemcategory.ItemCategoryDao;
import com.turing.dao.itemcategory.ItemCategoryDaoImpl;
import com.turing.model.Item;
import com.turing.model.ItemCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl extends Dao implements ItemDao {
    public ItemCategoryDao itemCategoryDao;

    public ItemDaoImpl(ItemCategoryDao itemCategoryDao) {
        this.itemCategoryDao = itemCategoryDao;
    }

    @Override
    public int insert(Item item) {
        int id = 0;
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO item(name, price, itemcategory_id, stock_quantity, itemCode) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, item.getName());
            st.setInt(2, item.getPrice());
            st.setInt(3, item.getItemCategory().getId());
            st.setInt(4, 0);
            st.setString(5, item.getItemCode());
            st.executeUpdate();

            ResultSet rs=st.getGeneratedKeys();

            if(rs.next()){
                id=rs.getInt(1);
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public void update(Item item) {
        try {
            PreparedStatement st = this.connection.prepareStatement("UPDATE item SET name=?, price=?, itemcategory_id=?, itemCode=? WHERE id=?");

            st.setString(1, item.getName());
            st.setInt(2, item.getPrice());
            st.setInt(3, item.getItemCategory().getId());
            st.setString(4, item.getItemCode());
            st.setInt(5, item.getId());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Item item) {
        try {
            Statement st =  connection.createStatement();
            st.executeUpdate("DELETE FROM item WHERE id=" + item.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item get(Item item) {
        Item item1 = null;
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM item WHERE id = " + item.getId());

            if(resultSet.next()) {
                item1 = this.parseItem(resultSet);
            }

            return item1;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from item");

            while (resultSet.next()) {
                items.add(this.parseItem(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private Item parseItem(ResultSet resultSet) {
        Item item = null;
        try {
            item = Item.parseItem(resultSet);
            item.setItemCategory(this.itemCategoryDao.get(item.getItemCategory()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    // Testing
    public static void main(String[] args) {
        ItemCategoryDao itemCategoryDao = new ItemCategoryDaoImpl();
        ItemDao itemDao = new ItemDaoImpl(itemCategoryDao);

        testInsert(itemDao, itemCategoryDao);
    }

    private static void testInsert(ItemDao itemDao, ItemCategoryDao itemCategoryDao) {
        ItemCategory itemCategory = new ItemCategory(2);
        itemCategory = itemCategoryDao.get(itemCategory);

        Item item = new Item();
        item.setItemCode("P001");
        item.setName("Milk");
        item.setPrice(200);
        item.setItemCategory(itemCategory);

        itemDao.insert(item);
    }

    private static void testUpdate(ItemDao itemDao, ItemCategoryDao itemCategoryDao) {
        ItemCategory itemCategory = new ItemCategory(3);
        itemCategory = itemCategoryDao.get(itemCategory);

        Item item = new Item();
        item.setItemCode("P001");
        item.setId(1);
        item.setName("Milk");
        item.setPrice(200);
        item.setItemCategory(itemCategory);

        itemDao.update(item);
    }

    private static void testDelete(ItemDao itemDao) {
        Item item  = new Item();
        item.setId(1);
        itemDao.delete(item);
    }

    private static void testGet(ItemDao itemDao, ItemCategoryDao itemCategoryDao) {
        Item item = new Item(2);
        System.out.println(itemDao.get(item));
    }

    private static void testGetAll(ItemDao itemDao, ItemCategoryDao itemCategoryDao) {
        for (Item item: itemDao.getAll()) {
            System.out.println(item);
        }
    }

}
