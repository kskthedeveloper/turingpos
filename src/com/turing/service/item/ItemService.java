package com.turing.service.item;

import com.turing.model.Item;
import com.turing.model.ItemCategory;

import java.util.List;

public interface ItemService {
    int save(String itemCode, String itemName, int price, ItemCategory itemCategory, int stockQuantity);
    void edit(int id, String itemCode, String itemName,int price, ItemCategory itemCategory, int stockQuantity);

    List<Item> getAll();
}
