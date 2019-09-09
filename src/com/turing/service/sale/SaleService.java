package com.turing.service.sale;

import com.turing.model.Sale;
import com.turing.model.TransitionItem;
import com.turing.model.User;

import java.sql.Date;
import java.util.List;

public interface SaleService {
    int addSale(Date date, User user, List<TransitionItem> itemList);
    int updateSale(Date date, User user, List<TransitionItem> itemList);
    List<Sale> getAll();
}
