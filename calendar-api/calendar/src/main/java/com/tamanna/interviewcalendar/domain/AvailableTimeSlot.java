package com.tamanna.interviewcalendar.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "available_time_slot")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(of = {"id", "dateFrom", "dateUntil", "timeFrom", "timeUntil"})
@EqualsAndHashCode(of = {"id", "dateFrom", "dateUntil", "timeFrom", "timeUntil", "user"}, callSuper = false)
public class AvailableTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "available_time_slot_seq")
    @SequenceGenerator(name = "available_time_slot_seq", sequenceName = "available_time_slot_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_until")
    private LocalDate dateUntil;

    @Column(name = "time_from")
    private LocalTime timeFrom;

    @Column(name = "time_until")
    private LocalTime timeUntil;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "system_user_id")
    private User user;
}
