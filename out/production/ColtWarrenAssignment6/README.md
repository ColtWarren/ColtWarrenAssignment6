# ColtWarrenAssignment6
Tesla Sales Data Analysis - Assignment #6
Overview
This Java application analyzes Tesla vehicle sales data from 2016 to 2019. It processes three CSV files (Model 3, Model S, and Model X), calculates yearly sales totals, and identifies the best/worst sales months for each model. The solution leverages Java Streams for efficient data processing.

Key Features
Parses sales data from CSV files

Calculates yearly sales totals per model

Identifies peak and lowest sales months

Uses Java Streams for data processing

Handles locale-specific date parsing

Requirements
Java 8+ (Streams API)

CSV files downloaded to project directory:

Model 3 Data

Model S Data

Model X Data

Implementation Details
Data Parsing:

Uses DateTimeFormatter with Locale.US for date parsing

Handles "MMM-yy" date format (e.g., "Jan-16")

Converts sales figures to integers

Stream Processing:

Groups sales data by year using Collectors.groupingBy

Calculates yearly totals with Collectors.summingInt

Identifies best/worst months using Comparator.comparingInt

Output Generation:

Prints formatted sales reports for each model

Displays yearly totals and monthly performance metrics

