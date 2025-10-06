package com.eventify.service;

import com.eventify.dtos.ParticipantRequest;
import com.eventify.dtos.ParticipantResponse;
import com.eventify.entity.Event;
import com.eventify.entity.Participant;
import com.eventify.mapper.ParticipantMapper;
import com.eventify.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final EventService eventService;
    private final ParticipantMapper participantMapper;

    public ParticipantService(ParticipantRepository participantRepository, EventService eventService, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.eventService = eventService;
        this.participantMapper = participantMapper;
    }

    @Transactional(readOnly = true)
    public List<ParticipantResponse> getEventParticipants(Long eventId) {
        eventService.findEventById(eventId);

        var participants = participantRepository.findByEventId(eventId);
        return participantMapper.toResponseList(participants);
    }


    public ParticipantResponse addParticipant(ParticipantRequest request){
        var participant = participantMapper.toParticipant(request);
        return participantMapper.toResponse(participantRepository.save(participant));
    }


    public Participant addParticipant(Event event, String name, String email, String phone) {
        if (participantRepository.existsByEventIdAndEmail(event.getId(), email)) {
            throw new IllegalArgumentException("Participant with email " + email + " already exists for this event");
        }

        Participant participant = new Participant();
        participant.setFirstName(name);
        participant.setEmail(email);
        participant.setPhone(phone);
        participant.setInvitationStatus(Participant.InvitationStatus.PENDING);
        participant.setEvent(event);

        return participantRepository.save(participant);
    }
}
