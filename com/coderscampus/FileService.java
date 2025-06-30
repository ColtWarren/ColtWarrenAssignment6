package com.coderscampus;

import java.io.*;

public class FileService {
    public String[] read(String filePath) throws IOException {
        int lineCount = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine();
            while (bufferedReader.readLine() != null)
                lineCount++;
        } catch (IOException e) {
            System.out.println("There was an error counting lines in " + filePath + " : " + e.getMessage());
        }

        String[] lines = new String[lineCount];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine();
            for (int i = 0; i < lineCount; i++) {
                lines[i] = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("There was an error counting lines in " + filePath + " : " + e.getMessage());
        }
        return lines;
    }
}
