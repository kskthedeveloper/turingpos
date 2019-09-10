package com.turing.service.transaction;

import com.turing.model.Transaction;
import com.turing.model.TransactionItem;
import com.turing.model.User;

import java.sql.Date;
import java.util.List;

public interface TransactionService {
    void addTransaction(Date date, User user, List<TransactionItem> itemList);
    int updateTransaction(Date date, User user, List<TransactionItem> itemList);
    List<Transaction> getAll();

    Transaction getById(int id);
}
