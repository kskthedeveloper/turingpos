package com.turing.dao.purchase;

import com.turing.model.Purchase;

import java.util.List;

public interface PurchaseDao {
    void insert(Purchase purchase);
    void update(Purchase purchase);
    void delete(Purchase purchase);
    Purchase get(Purchase purchase);
    List<Purchase> getAll();
}
