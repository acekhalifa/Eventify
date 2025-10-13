package com.eventify.controller;

import com.eventify.dtos.ParticipantDTO.UpdateStatusRequest;
import com.eventify.dtos.ParticipantRequest;
import com.eventify.dtos.ParticipantResponse;
import com.eventify.service.FileUploadService;
import com.eventify.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/participants")
@Tag(name = "Participants", description = "Participant management APIs")
public class ParticipantController {

    private final ParticipantService participantService;
    private final FileUploadService fileUploadService;

    @Autowired
    public ParticipantController(ParticipantService participantService, FileUploadService fileUploadService) {
        this.participantService = participantService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping
    @Operation(summary = "Get all participants for an event",
            description = "Retrieves a paginated list of all participants for a specific event (user must own the event)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of participants"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public Page<ParticipantResponse> getEventParticipants(
            @Parameter(description = "Event ID") @PathVariable Long eventId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return participantService.getEventParticipants(eventId, pageable);
    }

    @PostMapping
    @Operation(summary = "Create a new participant", description = "Creates a new Participant with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Participant created successfully",
                    content = @Content(schema = @Schema(implementation = ParticipantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ParticipantResponse> createEvent(
            @Parameter(description = "Event ID") @PathVariable Long eventId,
            @Valid @RequestBody ParticipantRequest request) {
        ParticipantResponse response = participantService.addParticipant(eventId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Bulk upload participants",
            description = "Upload CSV containing participant details. " +
                    "File must have headers: name, email, phone (optional)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File processed successfully",
                    content = @Content(schema = @Schema(implementation = ParticipantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file format or content"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<ParticipantResponse>> uploadParticipants(
            @Parameter(description = "Event ID") @PathVariable Long eventId,
            @Parameter(description = "CSV file containing participants")
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        var response = fileUploadService.uploadParticipants(eventId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PatchMapping("/{participantId}/status")
    @Operation(summary = "Update participant invitation status",
            description = "Updates the invitation status of a participant (ACCEPTED or DECLINED). User must own the event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully",
                    content = @Content(schema = @Schema(implementation = ParticipantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Event or Participant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ParticipantResponse updateParticipantStatus(
            @Parameter(description = "Event ID") @PathVariable Long eventId,
            @Parameter(description = "Participant ID") @PathVariable Long participantId,
            @Valid @RequestBody UpdateStatusRequest request) {
        return participantService.updateParticipantStatus(eventId, participantId, request);
    }
}
