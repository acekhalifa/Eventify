package com.eventify.mapper;

import com.eventify.dtos.EventRequest;
import com.eventify.dtos.EventResponse;
import com.eventify.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
//        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Event toEntity(EventRequest request);

    @Mapping(target = "participantCount", expression = "java(event.getParticipants().size())")
    EventResponse toResponse(Event event);
    List<EventResponse> toEventResponseList(Collection<Event> events);
}