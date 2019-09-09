package com.turing.dao.sale;

import com.turing.model.Sale;
import com.turing.model.TransitionItem;

import java.sql.Date;
import java.time.Month;
import java.time.Year;
import java.util.List;

public interface SaleDao {
    void insert(Sale sale);
    void update(Sale sale);
    void delete(Sale sale);
    Sale get(Sale sale);
    List<Sale> getAll();
    List<Sale> getSalesByCashier(int cashierId);
    List<Sale> getSalesByDate(Date startDate, Date endDate);
    List<Sale> getSalesByCashierAndDate(int cashierId, Date startDate, Date endDate);
    List<Sale> getSalesByMonth(Month month, int year);
}
