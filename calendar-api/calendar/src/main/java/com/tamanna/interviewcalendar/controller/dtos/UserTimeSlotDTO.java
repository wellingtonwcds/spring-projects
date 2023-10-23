package com.tamanna.interviewcalendar.controller.dtos;

import com.tamanna.interviewcalendar.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"user", "dateTimeSlot"})
public class UserTimeSlotDTO {
    private User user;
    private LocalDateTime dateTimeSlot;
}
