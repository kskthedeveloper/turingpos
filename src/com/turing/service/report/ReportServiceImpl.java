package com.turing.service.report;

import com.turing.dao.sale.SaleDao;
import com.turing.dao.sale.SaleDaoImpl;
import com.turing.model.TransactionItem;
import com.turing.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReportServiceImpl implements ReportService{
    SaleDao saleDao;
    public static ReportService reportService;

    private ReportServiceImpl() {
        saleDao = new SaleDaoImpl();
    }

    @Override
    public List<TransactionItem> getSaleReportByDate(Date startDate, Date endDate) {
        try {
            return saleDao.getSaleReportByDate(startDate, endDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TransactionItem> getSaleReportByCashier(User user) {
         return saleDao.getSaleReportByCashier(user);
    }

    @Override
    public List<TransactionItem> getSaleReportByCashierAndDate(User user, Date startDate, Date endDate) {
        return saleDao.getSaleReportByCashierAndDate(user, startDate, endDate);
    }

    public static ReportService getReportService() {
        if (reportService == null) {
            reportService = new ReportServiceImpl();
        }
        return reportService;
    }
}
