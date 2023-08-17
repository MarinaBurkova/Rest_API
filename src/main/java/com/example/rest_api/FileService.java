package com.example.rest_api;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class FileService {

    // Чтение из файла
    public String getRandomString(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        // Если файл пустой
        if (lines.isEmpty()) {
            return "No data available";
        }
        Random random = new Random();
        int randomIndex = random.nextInt(lines.size());
        return lines.get(randomIndex);
    }

    // Запись в файл
    public static void writeToFile(User user, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename, true)) {

            String userData = user.toString();
            writer.write(userData);
        }
    }
}
