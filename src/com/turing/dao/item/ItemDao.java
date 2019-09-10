package com.turing.dao.item;

import com.turing.model.Item;

import java.util.List;

public interface ItemDao {
    int insert(Item item);
    void update(Item item);
    void delete(Item item);
    Item get(Item item);
    Item findByCode(String code);
    List<Item> getAll();
}
