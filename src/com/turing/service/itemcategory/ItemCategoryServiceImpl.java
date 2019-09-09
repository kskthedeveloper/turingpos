package com.turing.service.itemcategory;

import com.turing.dao.itemcategory.ItemCategoryDao;
import com.turing.dao.itemcategory.ItemCategoryDaoImpl;
import com.turing.model.ItemCategory;

import java.util.List;

public class ItemCategoryServiceImpl implements ItemCategoryService {
    ItemCategoryDao itemCategoryDao;

    public ItemCategoryServiceImpl() {
        this.itemCategoryDao = new ItemCategoryDaoImpl();
    }

    @Override
    public int save(String itemCategoryName) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setName(itemCategoryName);
        return this.itemCategoryDao.insert(itemCategory);
    }

    @Override
    public void edit(int id, String itemCategoryName) {

    }

    @Override
    public List<ItemCategory> getAll() {
        return this.itemCategoryDao.getAll();
    }

    @Override
    public void update(int itemCategoryId, String itemCategoryName) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(itemCategoryId);
        itemCategory.setName(itemCategoryName);

        this.itemCategoryDao.update(itemCategory);
    }
}
