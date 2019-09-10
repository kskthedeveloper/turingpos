package com.turing.service.transaction;

import com.turing.dao.sale.SaleDao;
import com.turing.dao.sale.SaleDaoImpl;
import com.turing.model.*;

import java.sql.Date;
import java.util.List;

public class SaleServiceImpl implements TransactionService {
    public static TransactionService saleService;
    public List<Item> itemList;
    private SaleDao saleDao;

    private SaleServiceImpl(){
        saleDao = new SaleDaoImpl();
    }

    @Override
    public void addTransaction(Date date, User user, List<TransactionItem> itemList) {
        Sale sale = new Sale();
        sale.setDate(date);
        sale.setUser(user);
        sale.setItemList(itemList);
        saleDao.insert(sale);
    }

    @Override
    public int updateTransaction(Date date, User user, List<TransactionItem> itemList) {
        return 0;
    }

    @Override
    public List<Transaction> getAll() {
        return saleDao.getAll();
    }

    @Override
    public Transaction getById(int id) {
        Sale sale = new Sale(id);
        return saleDao.get(sale);
    }

    public static TransactionService getSaleService() {
        if (saleService == null) {
            saleService = new SaleServiceImpl();
        }
        return saleService;
    }
}
