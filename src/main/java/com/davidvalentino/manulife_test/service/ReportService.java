package com.davidvalentino.manulife_test.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private static final String SAMPLE_TEMPLATE_PATH = "/templates/sample_report.jrxml";
    private static final String CASRNBID0131_TEMPLATE_PATH = "/templates/CASRNBID0131.jrxml";
    private static final String CASRNBID0131_SECOND_PAGE_TEMPLATE_PATH = "/templates/CASRNBID0131_page2.jrxml";
    private static final String CASRBL043A6ID_TEMPLATE_PATH = "/templates/CASRBL043A6ID.jrxml";
    private static final String OUTPUT_DIR = "output";
    private static final String SAMPLE_OUTPUT_FILE = "sample_report.pdf";
    private static final String CASRNBID0131_OUTPUT_FILE = "CASRNBID0131.pdf";
    private static final String CASRBL043A6ID_OUTPUT_FILE = "CASRBL043A6ID.pdf";

    public void generateReport() {
        generateSampleReport();
        generateCASRNBID0131Report();
        generateCASRBID043A6IDReport();
    }

    private void generateSampleReport() {
        try {
            System.out.println("Starting generating sample report...");
            InputStream jrxmlStream = getClass().getResourceAsStream(SAMPLE_TEMPLATE_PATH);

            if (jrxmlStream == null) {
                throw new JRException("Jasper file not found: " + SAMPLE_TEMPLATE_PATH + ". Stop generating sample report.");
            } else {
                System.out.println("Jasper file found! Continue generating sample report...");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Title", "Test Report");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            saveReportToFile(jasperPrint, SAMPLE_OUTPUT_FILE);

            System.out.println("Sample report generation complete.");
        } catch (JRException e) {
            System.err.println("Error generating sample report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void generateCASRNBID0131Report() {
        try {
            System.out.println("Starting generating CASRNIBD0131 report...");
            InputStream firstPageTemplate = getClass().getResourceAsStream(CASRNBID0131_TEMPLATE_PATH);
            InputStream secondPageTemplate = getClass().getResourceAsStream(CASRNBID0131_SECOND_PAGE_TEMPLATE_PATH);

            if (firstPageTemplate == null) {
                throw new JRException("Jasper file not found: " + CASRNBID0131_TEMPLATE_PATH + ". Stop generating CASRNBID0131 report.");
            } else if (secondPageTemplate == null) {
                throw new JRException("Jasper file not found: " + CASRNBID0131_SECOND_PAGE_TEMPLATE_PATH + ". Stop generating CASRNBID0131 report.");
            } else {
                System.out.println("Jasper file found! Continue generating CASRNBID0131 report...");
            }

            JasperReport firstPage = JasperCompileManager.compileReport(firstPageTemplate);
            JasperReport secondPage = JasperCompileManager.compileReport(secondPageTemplate);

            List<Map<String, Object>> dataList = createStaticData();
            List<Map<String, Object>> dataList2 = createStaticDataSecondPage();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataList2);

            Map<String, Object> parameters = createReportParameters();
            parameters.put("DATA_SET", dataSource);
            parameters.put("DATA_SET_2", dataSource2);
            parameters.put("CREATED_DATE", "30 January 2025");

            JasperPrint firstPagePrint = JasperFillManager.fillReport(firstPage, parameters, dataSource);
            JasperPrint secondPagePrint = JasperFillManager.fillReport(secondPage, parameters, dataSource2);

            List<JRPrintPage> pages = secondPagePrint.getPages();
            for (JRPrintPage page : pages) {
                firstPagePrint.addPage(page);
            }

            saveReportToFile(firstPagePrint, CASRNBID0131_OUTPUT_FILE);

            System.out.println("CASRNBID0131 report generation complete.");
        } catch (JRException e) {
            System.err.println("Error generating CASRNBID0131 report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void generateCASRBID043A6IDReport() {
        try {
            System.out.println("Starting generating CASRBL043A6ID report...");
            InputStream jrxmlStream = getClass().getResourceAsStream(CASRBL043A6ID_TEMPLATE_PATH);

            if (jrxmlStream == null) {
                throw new JRException("Jasper file not found: " + CASRBL043A6ID_TEMPLATE_PATH + ". Stop generating CASRBL043A6ID report.");
            } else {
                System.out.println("Jasper file found! Continue generating CASRBL043A6ID report...");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            List<Map<String, Object>> dataList = createCASRBID043A6IDData();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("POLIS_DATASET", dataSource);
            parameters.put("NO_LETTER", "B5130766884716 / 4897");
            parameters.put("LETTER_DATE", "30 January 2025");
            parameters.put("TRANSACTION_TYPE", "Pembayaran Premi");
            parameters.put("TRANSACTION_AMOUNT", "Rp 1.000.000,00");
            parameters.put("PAYMENT_TYPE", "Payment Gateway");
            parameters.put("BOX_TEXT", """
                    CLI_NM - 4207083737 \n
                    ADDR_1 - 4207083737 \n
                    ADDR_2 - 4207083737 \n
                    ADDR_3 - 4207083737 \n
                    ADDR_4 - 4207083737 \n
                    12310 \n
                    2017180627/BL043A6/20240322 \n
            """);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            saveReportToFile(jasperPrint, CASRBL043A6ID_OUTPUT_FILE);

            System.out.println("CASRBL043A6ID report generation complete.");
        } catch (JRException e) {
            System.err.println("Error generating CASRBL043A6ID report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Map<String, Object>> createStaticData() {
        return Arrays.asList(
                createData("Andi", "Senior UW", "*-ALl", "GRP001", "Active", 1000000.0, 500000.0, 750000.0, 300000.0),
                createData("Budi", "Junior UW", "*-ALl", "GRP002", "Active", 2000000.0, 1000000.0, 1500000.0, 600000.0),
                createData("Cici", "Senior UW", "*-ALl", "GRP003", "Inactive", 3000000.0, 1500000.0, 2250000.0, 900000.0),
                createData("Dedi", "LV2", "M", "GRP004", "Active", 4000000.0, 2000000.0, 3000000.0, 1200000.0),
                createData("Euis", "LV1", "NM", "GRP004", "Active", 5000000.0, 2500000.0, 3750000.0, 1500000.0)
        );
    }

    private List<Map<String, Object>> createStaticDataSecondPage() {
        return Arrays.asList(
                createDataSecondPage("Fendi", "Senior UW", "*-ALl", "GRP001", "Active", 350, 100, 250, 75, 150, 111, 99.99),
                createDataSecondPage("Gina", "Junior UW", "*-ALl", "GRP002", "Active", 450, 200, 350, 100, 200, 222, 199.99),
                createDataSecondPage("Hadi", "Senior UW", "*-ALl", "GRP003", "Inactive", 550, 300, 450, 125, 250, 333, 299.99),
                createDataSecondPage("Ivan", "LV2", "M", "GRP004", "Active", 650, 400, 550, 150, 300, 444, 399.99),
                createDataSecondPage("Joni", "LV1", "NM", "GRP004", "Active", 750, 500, 650, 175, 350, 555, 499.99)
        );
    }

    private List<Map<String, Object>> createCASRBID043A6IDData() {
        return Arrays.asList(
            createDataCASRBID043A6ID("20012221829", "30 January 2025", "RUPIAH", "Rp 1.000.000,00", "TAHUNAN"),
            createDataCASRBID043A6ID("20012221829", "30 January 2025", "RUPIAH", "Rp 1.000.000,00", "TAHUNAN")
        );
    }

    private Map<String, Object> createData(String name, String groupDesc, String medInd, String groupId, String status,
                                           double maxLifeRisk, double maxADDBRisk, double maxCIRisk, double maxTDPRisk) {
        Map<String, Object> row = new HashMap<>();
        row.put("name", name);
        row.put("groupDesc", groupDesc);
        row.put("medInd", medInd);
        row.put("groupId", groupId);
        row.put("status", status);
        row.put("maxLifeRisk", maxLifeRisk);
        row.put("maxADDBRisk", maxADDBRisk);
        row.put("maxCIRisk", maxCIRisk);
        row.put("maxTDPRisk", maxTDPRisk);
        return row;
    }

    private Map<String, Object> createDataSecondPage(String name, String groupDesc, String medInd, String groupId, String status,
                                           double faceRatingBase, double faceRatingADDB, double faceRatingCI, double faceRatingTPD, double faceRatingWPPB, double faceRatingHospital, double extraRateBase) {
        Map<String, Object> row = new HashMap<>();
        row.put("name", name);
        row.put("groupDesc", groupDesc);
        row.put("medInd", medInd);
        row.put("groupId", groupId);
        row.put("status", status);
        row.put("faceRatingBase", faceRatingBase);
        row.put("faceRatingADDB", faceRatingADDB);
        row.put("faceRatingCI", faceRatingCI);
        row.put("faceRatingTPD", faceRatingTPD);
        row.put("faceRatingWPPB", faceRatingWPPB);
        row.put("faceRatingHospital", faceRatingHospital);
        row.put("extraRateBase", extraRateBase);
        return row;
    }

    private Map<String, Object> createDataCASRBID043A6ID(String noPolis, String paymentDate, String currency, String premiumAmount, String paymentMethod) {
        Map<String, Object> row = new HashMap<>();
        row.put("NO_POLIS", noPolis);
        row.put("PAYMENT_DATE", paymentDate);
        row.put("CURRENCY", currency);
        row.put("PREMIUM_AMOUNT", premiumAmount);
        row.put("PAYMENT_METHOD", paymentMethod);
        return row;
    }

    private Map<String, Object> createReportParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Laporan CASRNBID0131");
        parameters.put("createdBy", "David");
        return parameters;
    }

    private void saveReportToFile(JasperPrint jasperPrint, String outputFileName) throws JRException {
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String outputPath = OUTPUT_DIR + File.separator + outputFileName;
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        System.out.println("Report saved at: " + outputPath);
    }
}