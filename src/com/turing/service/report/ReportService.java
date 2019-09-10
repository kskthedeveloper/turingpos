package com.turing.service.report;

import com.turing.model.TransactionItem;
import com.turing.model.User;

import java.sql.Date;
import java.util.List;

public interface ReportService {
    List<TransactionItem> getSaleReportByDate(Date startDate, Date endDate);
    List<TransactionItem> getSaleReportByCashier(User user);
    List<TransactionItem> getSaleReportByCashierAndDate(User user, Date startDate, Date endDate);
}
