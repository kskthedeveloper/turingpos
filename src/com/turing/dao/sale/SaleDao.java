package com.turing.dao.sale;

import com.turing.model.Sale;
import com.turing.model.Transaction;
import com.turing.model.TransactionItem;
import com.turing.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Month;
import java.util.List;

public interface SaleDao {
    void insert(Sale sale);
    void update(Sale sale);
    void delete(Sale sale);
    Transaction get(Sale sale);
    List<Transaction> getAll();
    List<Transaction> getSalesByCashier(int cashierId);
    List<Transaction> getSalesByDate(Date startDate, Date endDate);
    List<Transaction> getSalesByCashierAndDate(int cashierId, Date startDate, Date endDate);
    List<Transaction> getSalesByMonth(Month month, int year);

    List<TransactionItem> getSalesReportByMonth(Month month, int year);

    List<TransactionItem> getSaleReportByDate(Date startDate, Date endDate) throws SQLException;

    List<TransactionItem> getSaleReportByCashier(User user);

    List<TransactionItem> getSaleReportByCashierAndDate(User user, Date startDate, Date endDate);
}
