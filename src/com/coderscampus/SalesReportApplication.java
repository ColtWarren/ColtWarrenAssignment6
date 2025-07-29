package com.coderscampus;

import java.util.List;

public class SalesReportApplication {
    public static void main(String[] args){
        SalesService salesService = new SalesService();
        List<String> models = List.of("3", "S", "X");
        salesService.generateSalesReport(models);
    }
}
