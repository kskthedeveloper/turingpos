package com.turing.dao.itemcategory;

import com.turing.dao.Dao;
import com.turing.model.ItemCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemCategoryDaoImpl extends Dao implements ItemCategoryDao {
    @Override
    public int insert(ItemCategory itemCategory) {
        int id = 0;
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO item_category (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, itemCategory.getName());
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
    public void update(ItemCategory itemCategory) {
        try {
            PreparedStatement st = this.connection.prepareStatement("UPDATE item_category SET name=? WHERE id=?");
            st.setString(1, itemCategory.getName());
            st.setInt(2, itemCategory.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ItemCategory itemCategory) {
        try {
            Statement st =  connection.createStatement();
            st.executeUpdate("DELETE FROM item_category WHERE id=" + itemCategory.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItemCategory get(ItemCategory itemCategory) {
        ItemCategory itemCategory1 = null;
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM item_category WHERE id = " + itemCategory.getId());

            if(resultSet.next()) {
                itemCategory1 = ItemCategory.parseItemCategory(resultSet);
            }

            return itemCategory1;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ItemCategory> getAll() {
        List<ItemCategory> users = new ArrayList<ItemCategory>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from item_category");

            while (resultSet.next()) {
                users.add(ItemCategory.parseItemCategory(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    // ItemCategoryDao Testing
    public static void main(String[] args) {
        ItemCategoryDao itemCategoryDao = new ItemCategoryDaoImpl();
        testInsert(itemCategoryDao);
    }

    private static void testInsert(ItemCategoryDao itemCategoryDao) {
        ItemCategory itemCategory = new ItemCategory("drink");
        itemCategoryDao.insert(itemCategory);
    }

    private static void testGetAll(ItemCategoryDao itemCategoryDao) {
        for(ItemCategory itemCategory: itemCategoryDao.getAll()) {
            System.out.println(itemCategory);
        }
    }

    private static void testGet(ItemCategoryDao itemCategoryDao) {
        ItemCategory itemCategory = new ItemCategory(1, "food");
        System.out.println(itemCategoryDao.get(itemCategory));
    }

    private static void testUpdate(ItemCategoryDao itemCategoryDao) {
        ItemCategory itemCategory = new ItemCategory(1, "drink");
        itemCategoryDao.update(itemCategory);
    }

    private static void testDelete(ItemCategoryDao itemCategoryDao) {
        ItemCategory itemCategory = new ItemCategory(1, "drink");
        itemCategoryDao.delete(itemCategory);
    }

}
