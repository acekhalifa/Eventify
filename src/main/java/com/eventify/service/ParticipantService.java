package com.eventify.service;

import com.eventify.dtos.ParticipantDTO.UpdateStatusRequest;
import com.eventify.dtos.ParticipantRequest;
import com.eventify.dtos.ParticipantResponse;
import com.eventify.entity.Event;
import com.eventify.entity.Participant;
import com.eventify.entity.User;
import com.eventify.exception.ResourceNotFoundException;
import com.eventify.mapper.ParticipantMapper;
import com.eventify.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final EventService eventService;
    private final ParticipantMapper participantMapper;
    private final UserService userService;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, UserService userService,
                              EventService eventService, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.eventService = eventService;
        this.participantMapper = participantMapper;
        this.userService = userService;
    }

    public Page<ParticipantResponse> getEventParticipants(Long eventId, Pageable pageable) {
        User currentUser = getCurrentUser();

        // Verify event exists and belongs to current user
        eventService.findEventByIdAndUserId(eventId, currentUser.getId());

        Page<Participant> participants = participantRepository.findByEventId(eventId, pageable);
        return participants.map(participantMapper::toResponse);
    }


    public ParticipantResponse addParticipant(long eventId, ParticipantRequest request) {
        var user = getCurrentUser();
        var event = eventService.findEventByIdAndUserId(eventId, user.getId());
        if (event == null)
            throw new ResourceNotFoundException("Event with ID: " + eventId + " doesn't exist for current user");
        var participant = participantMapper.toParticipant(request);
        participant.setEvent(event);
        return participantMapper.toResponse(participantRepository.save(participant));
    }


    public ParticipantResponse addParticipant(Event event, String name, String email, String phone) {
        var user = getCurrentUser();
        long id = event.getId();
        event = eventService.findEventByIdAndUserId(id, user.getId());

        if (participantRepository.existsByEventIdAndEmail(event.getId(), email)) {
            throw new IllegalArgumentException("Participant with email " + email + " already exists for this event");
        }

        Participant participant = new Participant();
        participant.setFirstName(name);
        participant.setLastName(name);
        participant.setEmail(email);
        participant.setPhone(phone);
        participant.setInvitationStatus(Participant.InvitationStatus.PENDING);
        participant.setEvent(event);

        return participantMapper.toResponse(participantRepository.save(participant));
    }

    public ParticipantResponse updateParticipantStatus(Long eventId, Long participantId,
                                                       UpdateStatusRequest request) {
        User currentUser = getCurrentUser();

        eventService.findEventByIdAndUserId(eventId, currentUser.getId());

        Participant participant = participantRepository.findByIdAndEventId(participantId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Participant not found with ID: " + participantId + " for event: " + eventId));

        participant.setInvitationStatus(request.getInvitationStatus());

        Participant updatedParticipant = participantRepository.save(participant);
        
        return participantMapper.toResponse(updatedParticipant);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }
}
