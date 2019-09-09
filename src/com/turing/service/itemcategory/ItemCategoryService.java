package com.turing.service.itemcategory;

import com.turing.model.ItemCategory;

import java.util.List;

public interface ItemCategoryService {
    int save(String itemCategoryName);
    void edit(int id, String itemCategoryName);

    List<ItemCategory> getAll();

    void update(int itemCategoryId, String itemCategoryName);
}
