package com.coderscampus;

import java.io.IOException;

public class SalesReportApplication {
    public static void main(String[] args) throws IOException {
        SalesService salesService = new SalesService();
        salesService.generateSalesReport();
        System.out.println("meow");
    }
}
