package com.davidvalentino.manulife_test.service;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    public void generateReport() {
        try {
            // Load .jrxml dari resources (tanpa menyimpan .jasper)
            InputStream jrxmlStream = getClass().getResourceAsStream("/templates/sample_report.jrxml");

            if (jrxmlStream == null) {
                throw new JRException("Jasper file not found: /templates/sample_report.jrxml");
            }

            // Compile langsung di memory
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            // Parameter untuk report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Title", "Test Report");

            // Generate JasperPrint tanpa menyimpan .jasper
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Simpan ke PDF
            File outputDir = new File("output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            String outputPath = "output/sample_report.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
            System.out.println("Report saved at: " + outputPath);

            // Buka otomatis jika OS mendukung
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(outputPath));
            }

        } catch (JRException | IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }
}
