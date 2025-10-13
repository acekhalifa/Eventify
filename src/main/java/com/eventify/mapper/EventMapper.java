package com.eventify.mapper;

import com.eventify.dtos.EventRequest;
import com.eventify.dtos.EventResponse;
import com.eventify.entity.Event;
import org.mapstruct.*;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromUpdateRequest(EventRequest updateRequest, @MappingTarget Event event);
}