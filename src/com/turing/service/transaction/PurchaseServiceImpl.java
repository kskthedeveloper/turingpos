package com.turing.service.transaction;

import com.turing.dao.purchase.PurchaseDao;
import com.turing.dao.purchase.PurchaseDaoImpl;
import com.turing.model.*;

import java.sql.Date;
import java.util.List;

public class PurchaseServiceImpl implements TransactionService {
    public static TransactionService purchaseService;
    public List<Item> itemList;
    private PurchaseDao purchaseDao;

    private PurchaseServiceImpl() {
        purchaseDao = new PurchaseDaoImpl();
    }

    @Override
    public void addTransaction(Date date, User user, List<TransactionItem> itemList) {
        Purchase purchase = new Purchase();
        purchase.setDate(date);
        purchase.setUser(user);
        purchase.setItemList(itemList);
        purchaseDao.insert(purchase);
    }

    @Override
    public int updateTransaction(Date date, User user, List<TransactionItem> itemList) {
        return 0;
    }

    @Override
    public List<Transaction> getAll() {
        return purchaseDao.getAll();
    }

    @Override
    public Transaction getById(int id) {
        return purchaseDao.get(new Purchase(id));
    }

    public static TransactionService getPurchaseService() {
        if(purchaseService == null) {
            purchaseService = new PurchaseServiceImpl();
        }
        return purchaseService;
    }
}
