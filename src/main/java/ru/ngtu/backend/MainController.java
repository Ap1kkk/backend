package ru.ngtu.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    @PostMapping("/api/save-data")
    public String saveData(@RequestBody InputDto inputDto) {
        String data = inputDto.getData();
        log.info("Writing data: {}", data);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/data.txt", true))) {
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
