package com.turing.dao.itemcategory;

import com.turing.model.ItemCategory;

import java.util.List;

public interface ItemCategoryDao {
    int insert(ItemCategory itemCategory);
    void update(ItemCategory itemCategory);
    void delete(ItemCategory itemCategory);
    ItemCategory get(ItemCategory itemCategory);
    List<ItemCategory> getAll();
}
