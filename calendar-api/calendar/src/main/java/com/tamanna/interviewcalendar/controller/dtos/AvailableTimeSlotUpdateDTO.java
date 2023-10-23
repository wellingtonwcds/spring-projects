package com.tamanna.interviewcalendar.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AvailableTimeSlotUpdateDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull(message = "dateFrom is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = true, value = "2021-01-01", example = "2021-01-01", dataType = "time")
    private LocalDate dateFrom;

    @NotNull(message = "dateUntil is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = true, value = "2021-01-05", example = "2021-01-05", dataType = "time")
    private LocalDate dateUntil;

    @NotNull(message = "timeFrom is mandatory")
    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(required = true, value = "16:00:00", example = "16:00:00", dataType = "time")
    private LocalTime timeFrom;

    @NotNull(message = "timeUntil is mandatory")
    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(required = true, value = "21:00:00", example = "21:00:00", dataType = "time")
    private LocalTime timeUntil;
}
