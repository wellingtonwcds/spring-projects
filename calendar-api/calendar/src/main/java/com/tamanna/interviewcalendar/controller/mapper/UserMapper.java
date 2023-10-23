package com.tamanna.interviewcalendar.controller.mapper;

import com.tamanna.interviewcalendar.controller.dtos.UserDTO;
import com.tamanna.interviewcalendar.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
    })
    User toEntity(UserDTO dto);

    @Mappings({
            @Mapping(target = "id", source = "e.id"),
    })
    UserDTO toDTO(User e);

    @Mappings({
            @Mapping(target = "id", source = "e.id"),
    })
    List<UserDTO> toListDTO(List<User> e);
}
