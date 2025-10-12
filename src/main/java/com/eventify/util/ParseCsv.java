package com.eventify.util;

import com.eventify.service.FileUploadService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ParseCsv {
    private int totalRecords;

    private List<FileUploadService.ParticipantData> parseCSV(MultipartFile file) {
        List<FileUploadService.ParticipantData> participants = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.builder()
                             .setHeader()
                             .setSkipHeaderRecord(true)
                             .setIgnoreHeaderCase(true)
                             .setTrim(true)
                             .build())) {

            for (CSVRecord record : csvParser) {
                ++totalRecords;
                try {
                    FileUploadService.ParticipantData data = new FileUploadService.ParticipantData();
                    data.setName(record.get("name"));
                    data.setEmail(record.get("email"));
                    data.setPhone(record.isMapped("phone") ? record.get("phone") : "");
                    participants.add(data);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
                }
            }

        } catch (IOException e) {

            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }

        return participants;
    }

    public int getTotalRecords() {
        return totalRecords;
    }
}
