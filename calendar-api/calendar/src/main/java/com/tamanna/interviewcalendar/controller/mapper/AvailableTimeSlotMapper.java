package com.tamanna.interviewcalendar.controller.mapper;

import com.tamanna.interviewcalendar.controller.dtos.AvailableTimeSlotDTO;
import com.tamanna.interviewcalendar.controller.dtos.AvailableTimeSlotUpdateDTO;
import com.tamanna.interviewcalendar.domain.AvailableTimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailableTimeSlotMapper {

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "user.id", source = "dto.userId"),
            @Mapping(target = "timeUntil", source = "dto.timeUntil", qualifiedByName = "removeOneSecond")
    })
    AvailableTimeSlot toEntity(AvailableTimeSlotDTO dto);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "timeUntil", source = "dto.timeUntil", qualifiedByName = "removeOneSecond")
    })
    AvailableTimeSlot toEntity(AvailableTimeSlotUpdateDTO dto);

    @Mappings({
            @Mapping(target = "id", source = "e.id"),
            @Mapping(target = "userId", source = "e.user.id"),
            @Mapping(target = "timeUntil", source = "e.timeUntil", qualifiedByName = "addOneSecond")
    })
    AvailableTimeSlotDTO toDTO(AvailableTimeSlot e);

    @Mappings({
            @Mapping(target = "id", source = "e.id"),
            @Mapping(target = "userId", source = "e.user.id"),
    })
    List<AvailableTimeSlotDTO> toListDTO(List<AvailableTimeSlot> entityList);


    @Named("addOneSecond")
    public static LocalTime addOneSecond(LocalTime localTime) {
        return localTime.plusSeconds(1L);
    }

    @Named("removeOneSecond")
    public static LocalTime removeOneSecond(LocalTime localTime) {
        return localTime.minusSeconds(1L);
    }
}
