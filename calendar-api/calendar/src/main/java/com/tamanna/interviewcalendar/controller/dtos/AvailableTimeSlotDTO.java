package com.tamanna.interviewcalendar.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AvailableTimeSlotDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull(message = "userId is mandatory")
    @Min(value = 1, message = "UserId should be greater than 0")
    private Long userId;

    @NotNull(message = "dateFrom is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = true, value = "2021-01-25", example = "2021-01-01", dataType = "time")
    private LocalDate dateFrom;

    @NotNull(message = "dateUntil is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = true, value = "2021-12-23", example = "2021-01-01", dataType = "time")
    private LocalDate dateUntil;

    @NotNull(message = "timeFrom is mandatory")
    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(required = true, value = "08:00:00", example = "08:00:00", dataType = "time")
    private LocalTime timeFrom;

    @NotNull(message = "timeUntil is mandatory")
    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(required = true, value = "16:00:00", example = "16:00:00", dataType = "time")
    private LocalTime timeUntil;

    @AssertTrue(message = "dateUntil should be equals or after dateFrom")
    private boolean isDateFromDateUntilValid() {
        return dateFrom != null && dateUntil != null && (dateUntil.isEqual(dateFrom) || dateUntil.isAfter(dateFrom));
    }

    @AssertTrue(message = "timeUntil should after timeFrom")
    private boolean isTimeFromTimeUntilValid() {
        return timeFrom != null && timeUntil != null && timeUntil.minusSeconds(1).isAfter(timeFrom);
    }

    @AssertTrue(message = "timeFrom and timeUntil should be ex: 10:00:00,11:00:00, 15:00:00. Never 10:01:10 or 16:15:12")
    private boolean isStartOfHour() {
        return timeFrom != null && timeUntil != null && timeFrom.getSecond() == 0 && timeFrom.getMinute() == 0 && timeUntil.getSecond() == 0 && timeUntil.getMinute() == 0;
    }
}
