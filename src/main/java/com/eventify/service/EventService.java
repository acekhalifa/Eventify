package com.eventify.service;

import com.eventify.dtos.EventRequest;
import com.eventify.dtos.EventResponse;
import com.eventify.entity.Event;
import com.eventify.exception.ResourceNotFoundException;
import com.eventify.mapper.EventMapper;
import com.eventify.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService{
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Transactional
    public EventResponse createEvent(EventRequest request) {

        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());

        Event savedEvent = eventRepository.save(event);

        return eventMapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    public EventResponse getEventById(Long id) {
        Event event = findEventById(id);
        return eventMapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents() {
        var eventsList = eventRepository.findAll();
        return eventMapper.toEventResponseList(eventsList);
    }

    @Transactional
    public EventResponse updateEvent(Long id, EventRequest request) {

        Event event = findEventById(id);
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());

        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toResponse(updatedEvent);
    }

    @Transactional
    public void deleteEvent(Long id) {
        Event event = findEventById(id);
        eventRepository.delete(event);
    }

    @Transactional(readOnly = true)
    public List<EventResponse> searchEvents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEvents();
        }

        var events = eventRepository.searchEvents(keyword.trim());
        return eventMapper.toEventResponseList(events);
    }

    public Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
    }
}
