package com.coderscampus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public String[] read(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine(); // Skip header
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
        }
        return lines.toArray(new String[0]);
    }
}