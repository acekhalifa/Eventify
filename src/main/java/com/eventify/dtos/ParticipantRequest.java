package com.eventify.dtos;

import com.eventify.entity.Participant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ParticipantRequest {

    @NotNull(message = "First Name is required")
    @Schema(description = "Participant first name", example = "Anas")
    private String firstName;

    @NotNull(message = " Last Name is required")
    @Schema(description = "Participant last name", example = "Yakubu")
    private String lastName;

    @NotNull(message = "email is required")
    @Email(message = "Enter a valid email")
    @Schema(description = "Participant email", example = "anasyakubu@gmail.com")
    private String email;

    @Schema(description = "Participant phone", example = "07062194905")
    private String phone;

    @Schema(description = "Invitation status", example = "PENDING")
    private Participant.InvitationStatus invitationStatus;

    public ParticipantRequest(String firstName, String lastName, String email, String phone, Participant.InvitationStatus invitationStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.invitationStatus = invitationStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantRequest that = (ParticipantRequest) o;
        return Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPhone(), that.getPhone()) && getInvitationStatus() == that.getInvitationStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail(), getPhone(), getInvitationStatus());
    }

    @Override
    public String toString() {
        return "ParticipantRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", invitationStatus=" + invitationStatus +
                '}';
    }
}
