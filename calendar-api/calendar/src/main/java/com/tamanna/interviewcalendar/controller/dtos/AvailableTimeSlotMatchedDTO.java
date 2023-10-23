package com.tamanna.interviewcalendar.controller.dtos;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AvailableTimeSlotMatchedDTO {

    private UserDTO candidate;
    private List<InterviewerTimeSlotDTO> interviewerTimeSlot;
}
