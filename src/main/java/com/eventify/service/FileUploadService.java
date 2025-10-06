package com.eventify.service;

import com.eventify.dtos.BulkUploadResponse;
import com.eventify.entity.Event;
import com.eventify.entity.Participant;
import com.eventify.exception.ResourceNotFoundException;
import com.eventify.mapper.ParticipantMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {

    private final ParticipantService participantService;
    private final EventService eventService;
    private final ParticipantMapper participantMapper;

    @Autowired
    public FileUploadService(ParticipantService participantService, EventService eventService, ParticipantMapper participantMapper) {
        this.participantService = participantService;
        this.eventService = eventService;
        this.participantMapper = participantMapper;
    }

    public BulkUploadResponse uploadParticipants(Long eventId, MultipartFile file){
        var event = eventService.findEventById(eventId);
        if(event == null) throw new ResourceNotFoundException("event with the id " +eventId+ " not found");
        if(file == null) throw new IllegalArgumentException("File is required");
        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.isBlank()){
            throw new IllegalArgumentException("Filename is required");
        }
        if(!fileName.endsWith("csv")){
            throw new IllegalArgumentException("Unsupported File Format. Only CSV files supported for now");
        }
        List<ParticipantData> participantsData = parseCSV(file);
        return processParticipants(event, participantsData);


    }

    private List<ParticipantData> parseCSV(MultipartFile file) {
        List<ParticipantData> participants = new ArrayList<>();
        int count = 0;
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
                try {
                    ++count;
                    ParticipantData data = new ParticipantData();
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

    private BulkUploadResponse processParticipants(Event event, List<ParticipantData> participantsData) {

        List<Participant> addedParticipants = new ArrayList<>();

        for (ParticipantData data : participantsData) {

                Participant participant = participantService.addParticipant(
                        event,
                        data.getName(),
                        data.getEmail(),
                        data.getPhone()
                );

                addedParticipants.add(participant);
        }

        BulkUploadResponse response = new BulkUploadResponse();
        response.setAddedParticipants(participantMapper.toResponseList(addedParticipants));

        return response;
    }

    private static class ParticipantData {
        @NotNull
        @NotBlank
        private String name;
        @NotNull
        @Email(message = "email provided should be valid")
        private String email;
        @Pattern(regexp = "^$|^\\d{11}$", message = "phone number should be empty or contain 11 digits")
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
