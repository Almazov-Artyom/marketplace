package ru.almaz.authservice.mapper;

import ru.almaz.authservice.dto.SignUpRequest;
import ru.almaz.authservice.dto.SignUpResponse;
import ru.almaz.authservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    SignUpResponse toSignUpResponse(User user);

    User toUser(SignUpRequest signUpRequest);
}
