package com.eventify.util;

import com.eventify.service.FileUploadService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseExcel {
    private int totalRecords;

    private List<FileUploadService.ParticipantData> parseToExcel(MultipartFile file) {
        List<FileUploadService.ParticipantData> participants = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Excel file is empty");
            }

            // Find column indices
            int nameCol = -1, emailCol = -1, phoneCol = -1;
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().toLowerCase().trim();
                if (header.equals("name")) nameCol = cell.getColumnIndex();
                else if (header.equals("email")) emailCol = cell.getColumnIndex();
                else if (header.equals("phone")) phoneCol = cell.getColumnIndex();
            }

            if (nameCol == -1 || emailCol == -1) {
                throw new IllegalArgumentException("Excel file must have 'name' and 'email' columns");
            }

            // Process data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                ++totalRecords;

                try {
                    FileUploadService.ParticipantData data = new FileUploadService.ParticipantData();
                    data.setName(getCellValue(row.getCell(nameCol)));
                    data.setEmail(getCellValue(row.getCell(emailCol)));
                    data.setPhone(phoneCol != -1 ? getCellValue(row.getCell(phoneCol)) : "");

                    if (!data.getName().isEmpty() && !data.getEmail().isEmpty()) {
                        participants.add(data);
                    }
                } catch (Exception e) {
//                   I'll add a logger to log the error soon. :)
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }

        return participants;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getDateCellValue().toString();
                }
                yield String.valueOf((long) cell.getNumericCellValue());
            }
            default -> "";
        };
    }
}
