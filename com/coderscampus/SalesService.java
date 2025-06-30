package com.coderscampus;

import java.io.IOException;
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

        public void generateSalesReport() {
            processModel("Model 3", "model3.csv");
            processModel("Model S", "modelS.csv");
            processModel("Model X", "modelX.csv");
        }

        private void processModel(String modelName, String fileName) {
            try {
                String[] lines = fileService.read(fileName);
                List<SalesData> salesData = Arrays.stream(lines)
                        .map(line -> {
                            try {
                                String[] parts = line.split("\\t");
                                YearMonth date = YearMonth.parse(parts[0], inputFormatter);
                                int sales = Integer.parseInt(parts[1].trim());
                                return new SalesData(date, sales);
                            } catch (Exception e) {
                                System.err.println("File error for " + line);
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

                SalesData bestMonth = salesData.stream()
                        .max(Comparator.comparingInt(SalesData::getSales))
                        .orElseThrow();

                SalesData worstMonth = salesData.stream()
                        .min(Comparator.comparingInt(SalesData::getSales))
                        .orElseThrow();

                printReport(modelName, yearlySales, bestMonth, worstMonth);

            } catch (IOException e) {
                System.err.println("File error for " + modelName + ": " + e.getMessage());
            }
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

