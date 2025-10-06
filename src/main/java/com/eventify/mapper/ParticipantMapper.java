package com.eventify.mapper;

import com.eventify.dtos.ParticipantRequest;
import com.eventify.dtos.ParticipantResponse;
import com.eventify.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ParticipantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Participant toParticipant(ParticipantRequest request);
    ParticipantResponse toResponse(Participant participant);
    List<ParticipantResponse> toResponseList(Collection<Participant> participants);
}
