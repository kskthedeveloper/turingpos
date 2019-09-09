package com.turing.service.item;

import com.turing.model.Item;
import com.turing.model.ItemCategory;

import java.util.List;

public interface ItemService {
    int save(String itemName, int price, ItemCategory itemCategory, int stockQuantity);
    void edit(int id, String itemName,int price, ItemCategory itemCategory, int stockQuantity);

    List<Item> getAll();

    void update(int id, String itemName,int price, ItemCategory itemCategory, int stockQuantity);
}
