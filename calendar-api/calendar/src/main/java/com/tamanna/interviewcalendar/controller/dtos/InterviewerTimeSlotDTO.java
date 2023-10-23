package com.tamanna.interviewcalendar.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InterviewerTimeSlotDTO {

    private UserDTO interviewer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(required = true, dataType = "dateTime")
    private List<LocalDateTime> availableSlots;
}


