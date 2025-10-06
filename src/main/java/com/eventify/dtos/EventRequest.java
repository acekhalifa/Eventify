package com.eventify.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Schema(description = "Request to create a new event")
public class EventRequest {

    @NotBlank(message = "Title is required")
    @Schema(description = "Event title", example = "Tech Conference 2025")
    private String title;

    @Schema(description = "Event description", example = "Annual technology conference")
    private String description;

    @NotNull(message = "Event date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Event date and time", example = "2025-12-15T10:00:00")
    private LocalDateTime eventDate;

    @NotBlank(message = "Location is required")
    @Schema(description = "Event location", example = "Lagos Convention Center")
    private String location;

    public EventRequest(String title, String description, LocalDateTime eventDate, String location) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventRequest that = (EventRequest) o;
        return Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getEventDate(), that.getEventDate()) && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription(), getEventDate(), getLocation());
    }

    @Override
    public String toString() {
        return "EventRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", location='" + location + '\'' +
                '}';
    }
}
