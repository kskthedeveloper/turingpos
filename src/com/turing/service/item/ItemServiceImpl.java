package com.turing.service.item;

import com.turing.dao.item.ItemDao;
import com.turing.dao.item.ItemDaoImpl;
import com.turing.dao.itemcategory.ItemCategoryDaoImpl;
import com.turing.model.Item;
import com.turing.model.ItemCategory;

import java.util.List;

// TODO: to check duplicate item code
public class ItemServiceImpl implements ItemService {
    ItemDao itemDao;

    public ItemServiceImpl() {
        this.itemDao = new ItemDaoImpl(new ItemCategoryDaoImpl());
    }

    @Override
    public int save(String itemCode, String itemName, int price, ItemCategory itemCategory, int stockQuantity) {
        Item item = new Item();
        item.setItemCode(itemCode);
        item.setName(itemName);
        item.setPrice(price);
        item.setItemCategory(itemCategory);
        item.setStockQuantity(stockQuantity);
        return itemDao.insert(item);
    }

    @Override
    public void edit(int id, String itemCode, String itemName, int price, ItemCategory itemCategory, int stockQuantity) {
        Item item = new Item();
        item.setId(id);
        item.setItemCode(itemCode);
        item.setName(itemName);
        item.setPrice(price);
        item.setItemCategory(itemCategory);
        item.setStockQuantity(stockQuantity);

        itemDao.update(item);
    }

    @Override
    public List<Item> getAll() {
        return itemDao.getAll();
    }

    @Override
    public Item findByCode(String code) {
        return itemDao.findByCode(code);
    }


}
