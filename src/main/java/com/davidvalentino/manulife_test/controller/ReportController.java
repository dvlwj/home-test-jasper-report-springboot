package com.davidvalentino.manulife_test.controller;

import com.davidvalentino.manulife_test.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;

@Controller
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    public void generateReport() throws JRException {
        System.out.println("Starting report generation...");
        reportService.generateReport();
        System.out.println("Report generation complete.");
    }
}
