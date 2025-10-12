package com.eventify.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class BulkUploadResponse {
    @Schema(description = "Total records processed", example = "100")
    private Integer totalRecords;

    @Schema(description = "Successfully added participants", example = "95")
    private Integer successCount;

    @Schema(description = "List of successfully added participants")
    private List<ParticipantResponse> addedParticipants;

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public List<ParticipantResponse> getAddedParticipants() {
        return addedParticipants;
    }

    public void setAddedParticipants(List<ParticipantResponse> addedParticipants) {
        this.addedParticipants = addedParticipants;
    }

    @Override
    public String toString() {
        return "BulkUploadResponse{" +
                "successCount=" + successCount +
                ", addedParticipants=" + addedParticipants +
                '}';
    }
}
