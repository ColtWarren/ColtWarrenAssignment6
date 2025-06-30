package com.coderscampus;

public class SalesReportApplication {
    public static void main(String[] args) {
        SalesService salesService = new SalesService();
        salesService.generateSalesReport();
    }
}
