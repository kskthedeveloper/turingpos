package com.turing.service.sale;

import com.turing.dao.sale.SaleDao;
import com.turing.dao.sale.SaleDaoImpl;
import com.turing.model.Item;
import com.turing.model.Sale;
import com.turing.model.TransitionItem;
import com.turing.model.User;

import java.sql.Date;
import java.util.List;

public class SaleServiceImpl implements SaleService {
    public static SaleService saleService;
    public List<Item> itemList;
    private SaleDao saleDao;

    private SaleServiceImpl(){
        saleDao = new SaleDaoImpl();
    }

    @Override
    public int addSale(Date date, User user, List<TransitionItem> itemList) {
        return 0;
    }

    @Override
    public int updateSale(Date date, User user, List<TransitionItem> itemList) {
        return 0;
    }

    @Override
    public List<Sale> getAll() {
        return saleDao.getAll();
    }

    public static SaleService getSaleService() {
        if (saleService == null) {
            saleService = new SaleServiceImpl();
        }
        return saleService;
    }
}
