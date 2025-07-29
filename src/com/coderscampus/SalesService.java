package com.coderscampus;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SalesService {
    private final FileService fileService = new FileService();
    private final DateTimeFormatter inputFormatter =
            DateTimeFormatter.ofPattern("MMM-yy").withLocale(Locale.US);
    private final DateTimeFormatter outputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM");

    public void generateSalesReport(List<String> models ) {

        for (String model : models) {
            processModel("Model " + model, "model" + model + ".csv");
        }
    }

    private void processModel(String modelName, String fileName) {
        List<String> lines = fileService.read(fileName);
        List<SalesData> salesData = lines.stream()
                .map(line -> {
                    try {
                        String[] parts = line.split(",");
                        YearMonth date = YearMonth.parse(parts[0], inputFormatter);
                        int sales = Integer.parseInt(parts[1].trim());
                        return new SalesData(date, sales);
                    } catch (Exception e) {
                        System.err.println("File error for " + line + " | Error: " + e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (salesData.isEmpty()) {
            System.err.println("File error for " + modelName);
            return;
        }

        Map<Integer, Integer> yearlySales = salesData.stream()
                .collect(Collectors.groupingBy(
                        data -> data.getDate().getYear(),
                        TreeMap::new,
                        Collectors.summingInt(SalesData::getSales)
                ));

        SalesData bestMonth = getBestMonth(salesData);

        SalesData worstMonth = getWorstMonth(salesData);

        printReport(modelName, yearlySales, bestMonth, worstMonth);

    }

    private static SalesData getWorstMonth(List<SalesData> salesData) {
        return salesData.stream()
                .min(Comparator.comparingInt(SalesData::getSales))
                .orElseThrow();
    }

    private static SalesData getBestMonth(List<SalesData> salesData) {
        return salesData.stream()
                .max(Comparator.comparingInt(SalesData::getSales))
                .orElseThrow();
    }

    private void printReport(String modelName,
                             Map<Integer, Integer> yearlySales,
                             SalesData bestMonth,
                             SalesData worstMonth) {

        System.out.println(modelName + " Yearly Sales Report");
        System.out.println("---------------------------");

        yearlySales.forEach((year, total) ->
                System.out.println(year + " -> " + total)
        );

        System.out.println("The best month for " + modelName + " was: "
                + bestMonth.getDate().format(outputFormatter));
        System.out.println("The worst month for " + modelName + " was: "
                + worstMonth.getDate().format(outputFormatter));
        System.out.println();
    }
}

