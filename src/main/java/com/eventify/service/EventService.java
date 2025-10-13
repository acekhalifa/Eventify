package com.eventify.service;

import com.eventify.dtos.EventRequest;
import com.eventify.dtos.EventResponse;
import com.eventify.entity.Event;
import com.eventify.entity.User;
import com.eventify.exception.ResourceNotFoundException;
import com.eventify.mapper.EventMapper;
import com.eventify.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper, UserService userService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.userService = userService;
    }

    @Transactional
    public EventResponse createEvent(EventRequest request) {
        User currentUser = getCurrentUser();
        Event event = eventMapper.toEntity(request);
        event.setUser(currentUser);
        
        Event savedEvent = eventRepository.save(event);

        return eventMapper.toResponse(savedEvent);
    }

    @Transactional(readOnly = true)
    public EventResponse getEventById(Long id) {
        User currentUser = getCurrentUser();

        var event = findEventByIdAndUserId(id, currentUser.getId());
        return eventMapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> getAllEvents(Pageable pageable) {
        var user = getCurrentUser();
        var events = eventRepository.findByUserId(user.getId(), pageable);
        return events.map(eventMapper::toResponse);
    }

    @Transactional
    public EventResponse updateEvent(Long id, EventRequest request) {
        User currentUser = getCurrentUser();
        Event event = findEventByIdAndUserId(id, currentUser.getId());
        eventMapper.updateEntityFromUpdateRequest(request, event);

        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toResponse(updatedEvent);
    }

    @Transactional
    public void deleteEvent(Long id) {
        var user = getCurrentUser();
        var event = findEventByIdAndUserId(id, user.getId());
        eventRepository.delete(event);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> searchEvents(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEvents(pageable);
        }
        var user = getCurrentUser();
        var events = eventRepository.searchEvents(user.getId(), keyword.trim(), pageable);
        return events.map(eventMapper::toResponse);
    }

    public Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
    }

    Event findEventByIdAndUserId(Long id, long userId) {
        return eventRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id + " for current user"));
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }

}
