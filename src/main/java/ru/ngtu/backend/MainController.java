package ru.ngtu.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final String fileName = "src/main/resources/data.txt";

    @GetMapping("/api/get-data")
    public DataDto getData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            DataDto dataDto = new DataDto();
            dataDto.setData(content.toString().trim());
            return dataDto;

        } catch (FileNotFoundException e) {
            log.error("File not found: {}", e.getMessage(), e);
            DataDto dataDto = new DataDto();
            dataDto.setData("");
            return dataDto;
        } catch (IOException e) {
            log.error("Error while read: {}", e.getMessage(), e);
            throw new RuntimeException("Ошибка при чтении файла", e);
        }
    }

    @PostMapping("/api/save-data")
    public String saveData(@RequestBody DataDto inputDataDto) {
        String data = inputDataDto.getData();
        log.info("Writing data: {}", data);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
            return data;
        } catch (IOException e) {
            String errorMessage = "Ошибка при сохранении данных: " + e.getMessage();
            log.error(errorMessage, e);
            return errorMessage;
        }
    }
}
