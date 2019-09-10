package com.turing.dao.purchase;

import com.turing.model.Purchase;
import com.turing.model.Transaction;

import java.util.List;

public interface PurchaseDao {
    void insert(Purchase purchase);
    void update(Purchase purchase);
    void delete(Purchase purchase);
    Transaction get(Purchase purchase);
    List<Transaction> getAll();
}
