package com.eventify.mapper;

import com.eventify.dtos.AuthResponse;
import com.eventify.dtos.RegisterRequest;
import com.eventify.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "token", ignore = true) // Set by service
    @Mapping(target = "type", constant = "Bearer")
    @Mapping(target = "userId", source = "id")
    AuthResponse toAuthResponse(User user);
}
