package com.eventify.dtos.ParticipantDTO;

import com.eventify.entity.Participant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Update participant invitation status request")
public class UpdateStatusRequest {

    @NotNull(message = "Invitation status is required")
    @Schema(description = "Invitation status", example = "ACCEPTED", allowableValues = {"ACCEPTED", "DECLINED"})
    private Participant.InvitationStatus invitationStatus;
}