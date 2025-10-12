package com.eventify.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Event() {
    }

    public Event(String title, Long id, String description, LocalDateTime eventDate, String location, List<Participant> participants, LocalDateTime createdAt) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.participants = participants;
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
        participant.setEvent(this);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
        participant.setEvent(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(getId(), event.getId()) && Objects.equals(getTitle(), event.getTitle()) && Objects.equals(getDescription(), event.getDescription()) && Objects.equals(getEventDate(), event.getEventDate()) && Objects.equals(getLocation(), event.getLocation()) && Objects.equals(getParticipants(), event.getParticipants()) && Objects.equals(getCreatedAt(), event.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getEventDate(), getLocation(), getParticipants(), getCreatedAt());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", location='" + location + '\'' +
                ", participants=" + participants +
                ", createdAt=" + createdAt +
                '}';
    }
}










































































