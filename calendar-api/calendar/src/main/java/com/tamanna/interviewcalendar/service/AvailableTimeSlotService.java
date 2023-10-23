package com.tamanna.interviewcalendar.service;

import com.tamanna.interviewcalendar.controller.dtos.AvailableTimeSlotMatchedDTO;
import com.tamanna.interviewcalendar.controller.dtos.InterviewerTimeSlotDTO;
import com.tamanna.interviewcalendar.controller.dtos.UserTimeSlotDTO;
import com.tamanna.interviewcalendar.controller.mapper.UserMapper;
import com.tamanna.interviewcalendar.domain.AvailableTimeSlot;
import com.tamanna.interviewcalendar.repository.AvailableTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AvailableTimeSlotService {

    private final AvailableTimeSlotRepository availableTimeSlotRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final LocalTime lastTimeSpan = LocalTime.parse("23:00:00");

    public List<AvailableTimeSlot> findAllByUserId(Long userId) {
        return availableTimeSlotRepository.findAllByUserId(userId);
    }

    public AvailableTimeSlot getById(Long id) {
        return availableTimeSlotRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("AvailableTimeSlot was not found with id: %d", id)));
    }

    public AvailableTimeSlot save(AvailableTimeSlot availableTimeSlot) {
        userService.getById(availableTimeSlot.getUser().getId());
        checkDuplicated(availableTimeSlot);
        return availableTimeSlotRepository.save(availableTimeSlot);
    }

    public AvailableTimeSlot update(Long id, AvailableTimeSlot availableTimeSlot) {
        AvailableTimeSlot availableTimeSlotPersisted = getById(id);
        availableTimeSlot.setUser(availableTimeSlotPersisted.getUser());
        checkDuplicated(availableTimeSlot, id);
        updateAttributes(availableTimeSlot, availableTimeSlotPersisted);
        return availableTimeSlotRepository.save(availableTimeSlotPersisted);
    }

    public void deleteById(Long id) {
        var availableTimeSlot = getById(id);
        availableTimeSlotRepository.delete(availableTimeSlot);
    }

    public AvailableTimeSlotMatchedDTO findAvailableTimeSlotMatchedBy(Long candidateId, Set<Long> interviewerIds) {
        final var candidate = userService.getById(candidateId);
        final var candidateAvailableLocalDateTime = getAvailableLocalDateTime(candidate.getAvailableTimeSlotList()).stream().map(UserTimeSlotDTO::getDateTimeSlot).collect(Collectors.toList());
        final var interviewersAvailableTimeSlots = availableTimeSlotRepository.findAllByUserIds(new ArrayList<>(interviewerIds));
        final var availableTimeSlotMatched =
                getAvailableLocalDateTime(interviewersAvailableTimeSlots).stream().filter(item -> candidateAvailableLocalDateTime.contains(item.getDateTimeSlot())).collect(Collectors.toList());
        return AvailableTimeSlotMatchedDTO.builder().candidate(userMapper.toDTO(candidate)).interviewerTimeSlot(buildUserTimeSlotDTO(availableTimeSlotMatched)).build();
    }

    public List<UserTimeSlotDTO> getAvailableLocalDateTime(final List<AvailableTimeSlot> availableTimeSlotList) {
        return availableTimeSlotList.stream().flatMap(availableTimeSlot -> {
            LocalDateTime startDateTime = LocalDateTime.of(availableTimeSlot.getDateFrom(), availableTimeSlot.getTimeFrom());
            LocalDateTime endDateTime = LocalDateTime.of(availableTimeSlot.getDateUntil(), availableTimeSlot.getTimeUntil());
            final var returnList = new ArrayList<UserTimeSlotDTO>();
            while (startDateTime.isBefore(endDateTime) || startDateTime.isEqual(endDateTime)) {
                LocalTime startTime = availableTimeSlot.getTimeFrom();
                while (!lastTimeSpan.equals(startTime) && startTime.isBefore(availableTimeSlot.getTimeUntil())) {
                    returnList.add(UserTimeSlotDTO.builder().dateTimeSlot(startDateTime).user(availableTimeSlot.getUser()).build());
                    startDateTime = startDateTime.plusHours(1);
                    startTime = startTime.plusHours(1);
                }
                startDateTime = LocalDateTime.of(startDateTime.plusDays(1).toLocalDate(), availableTimeSlot.getTimeFrom());
            }
            return returnList.stream();
        }).collect(Collectors.toSet()).stream().sorted(Comparator.comparing(UserTimeSlotDTO::getDateTimeSlot)).collect(Collectors.toList());
    }

    private void checkDuplicated(AvailableTimeSlot availableTimeSlot) {
        checkDuplicated(availableTimeSlot, null);
    }

    private void checkDuplicated(AvailableTimeSlot slot, Long idToIgnore) {
        List<AvailableTimeSlot> slots = availableTimeSlotRepository.findAllByUserIdAndDateFromAndDateUntilAndTimeFromAndTimeUntil(
                slot.getUser().getId(), slot.getDateFrom(), slot.getDateUntil(), slot.getTimeFrom(), slot.getTimeUntil());
        if (slots.size() > 0 && !slots.get(0).getId().equals(idToIgnore)) {
            throw new EntityExistsException("There is another Slots with the same attributes");
        }
    }

    private List<InterviewerTimeSlotDTO> buildUserTimeSlotDTO(List<UserTimeSlotDTO> interviewFiltered) {
        return interviewFiltered
                .stream()
                .collect(Collectors.groupingBy(UserTimeSlotDTO::getUser))
                .entrySet()
                .stream()
                .map(mapItem ->
                        InterviewerTimeSlotDTO
                                .builder()
                                .interviewer(userMapper.toDTO(mapItem.getKey()))
                                .availableSlots(mapItem.getValue().stream().map(UserTimeSlotDTO::getDateTimeSlot).collect(Collectors.toList()))
                                .build()
                ).collect(Collectors.toList());
    }

    private void updateAttributes(AvailableTimeSlot availableTimeSlot, AvailableTimeSlot availableTimeSlotPersisted) {
        availableTimeSlotPersisted.setTimeFrom(availableTimeSlot.getTimeFrom());
        availableTimeSlotPersisted.setTimeUntil(availableTimeSlot.getTimeUntil());
        availableTimeSlotPersisted.setDateFrom(availableTimeSlot.getDateFrom());
        availableTimeSlotPersisted.setDateUntil(availableTimeSlot.getDateUntil());
    }
}
