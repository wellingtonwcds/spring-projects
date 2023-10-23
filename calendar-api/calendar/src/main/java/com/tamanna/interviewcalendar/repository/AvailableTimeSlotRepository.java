package com.tamanna.interviewcalendar.repository;

import com.tamanna.interviewcalendar.domain.AvailableTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailableTimeSlotRepository extends JpaRepository<AvailableTimeSlot, Long> {

    @Query(value = "SELECT availableSlot FROM AvailableTimeSlot availableSlot join fetch availableSlot.user user WHERE user.id in (?1)")
    List<AvailableTimeSlot> findAllByUserIds(List<Long> usersIds);

    @Query(value = "SELECT availableSlot FROM AvailableTimeSlot availableSlot join fetch availableSlot.user user WHERE user.id = ?1")
    List<AvailableTimeSlot> findAllByUserId(Long userId);

    List<AvailableTimeSlot> findAllByUserIdAndDateFromAndDateUntilAndTimeFromAndTimeUntil(Long userId, LocalDate dateFrom, LocalDate dateUntil, LocalTime timeFrom, LocalTime timeUntil);
}
