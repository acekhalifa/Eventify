package com.eventify.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Objects;

public class EventResponse {
    @Schema(description = "Event ID", example = "1")
    private Long id;

    @Schema(description = "Event title", example = "Tech Conference 2025")
    private String title;

    @Schema(description = "Event description")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Event date and time")
    private LocalDateTime eventDate;

    @Schema(description = "Event location")
    private String location;

    @Schema(description = "Number of participants")
    private Integer participantCount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    public EventResponse() {
    }

    public EventResponse(Long id, String title, String description, LocalDateTime eventDate, String location, Integer participantCount, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.participantCount = participantCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
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
        EventResponse that = (EventResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getEventDate(), that.getEventDate()) && Objects.equals(getLocation(), that.getLocation()) && Objects.equals(getParticipantCount(), that.getParticipantCount()) && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getEventDate(), getLocation(), getParticipantCount(), getCreatedAt());
    }

    @Override
    public String toString() {
        return "EventResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", location='" + location + '\'' +
                ", participantCount=" + participantCount +
                ", createdAt=" + createdAt +
                '}';
    }
}
