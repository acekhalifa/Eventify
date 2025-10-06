package com.eventify.dtos;

import com.eventify.entity.Participant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParticipantResponse{
    @Schema(description = "Participant ID", example = "1")
    private Long id;

    @Schema(description = "Participant name", example = "Anas Yakubu")
    private String name;

    @Schema(description = "Participant email", example = "anasyakubu@example.com")
    private String email;

    @Schema(description = "Participant phone", example = "07062194905")
    private String phone;

    @Schema(description = "Invitation status", example = "PENDING")
    private Participant.InvitationStatus invitationStatus;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Registration timestamp")
    private LocalDateTime createdAt;

    public ParticipantResponse() {
    }

    public ParticipantResponse(Long id, String name, String email, String phone, Participant.InvitationStatus invitationStatus, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.invitationStatus = invitationStatus;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Participant.InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(Participant.InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantResponse that = (ParticipantResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPhone(), that.getPhone()) && getInvitationStatus() == that.getInvitationStatus() && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPhone(), getInvitationStatus(), getCreatedAt());
    }

    @Override
    public String toString() {
        return "ParticipantResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", invitationStatus=" + invitationStatus +
                ", createdAt=" + createdAt +
                '}';
    }
}
