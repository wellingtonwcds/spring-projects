package com.tamanna.interviewcalendar.service;

import com.tamanna.interviewcalendar.controller.mapper.UserMapper;
import com.tamanna.interviewcalendar.controller.mapper.UserMapperImpl;
import com.tamanna.interviewcalendar.domain.AvailableTimeSlot;
import com.tamanna.interviewcalendar.domain.RoleType;
import com.tamanna.interviewcalendar.domain.User;
import com.tamanna.interviewcalendar.repository.AvailableTimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AvailableTimeSlotServiceTest {

    @InjectMocks
    private AvailableTimeSlotService testee;

    @Mock
    private UserService userService;

    @Mock
    private AvailableTimeSlotRepository availableTimeSlotRepository;

    private final UserMapper userMapper = new UserMapperImpl();

    private User candidate;
    private User interviewer1;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(testee, "userMapper", userMapper);
        candidate = User.builder().id(1L).name("Candidate").roleType(RoleType.CANDIDATE).build();
        interviewer1 = User.builder().name("wellington").id(1L).roleType(RoleType.INTERVIEWER).build();
    }

    @Test
    public void shouldReturnSlotMatchedBetweenCandidateAndInterviewers() {


        var availableTimeSlotC = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("08:00:00")).timeUntil(LocalTime.parse("16:59:59")).dateFrom(LocalDate.parse("2021-10-01")).dateUntil(LocalDate.parse("2021-10-03")).user(candidate).build();
        candidate.setAvailableTimeSlotList(List.of(availableTimeSlotC));

        var allSlotsInterviewers = new ArrayList<>();
        var interviewerJoao = User.builder().id(2L).name("Interviewer Joao").roleType(RoleType.INTERVIEWER).build();
        var availableTimeSlotJoao = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("16:00:00")).timeUntil(LocalTime.parse("21:59:59")).dateFrom(LocalDate.parse("2021-10-03")).dateUntil(LocalDate.parse("2021-10-04")).user(interviewerJoao).build();
        allSlotsInterviewers.add(availableTimeSlotJoao);

        var interviewerPedro = User.builder().id(3L).name("Interviewer Pedro").roleType(RoleType.INTERVIEWER).build();
        var availableTimeSlotPedro = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("08:00:00")).timeUntil(LocalTime.parse("08:59:59")).dateFrom(LocalDate.parse("2021-10-01")).dateUntil(LocalDate.parse("2021-10-01")).user(interviewerPedro).build();
        allSlotsInterviewers.add(availableTimeSlotPedro);

        var interviewersIds = Set.of(interviewerJoao.getId(), interviewerPedro.getId());

        doReturn(candidate).when(userService).getById(candidate.getId());
        doReturn(allSlotsInterviewers).when(availableTimeSlotRepository).findAllByUserIds(new ArrayList<>(interviewersIds));

        var result = testee.findAvailableTimeSlotMatchedBy(candidate.getId(), interviewersIds);
        var slotMatchedJoao = LocalDateTime.parse("2021-10-03T16:00:00");
        var slotMatchedPedro = LocalDateTime.parse("2021-10-01T08:00:00");
        var slotsMatchedInterviewerJoao = result.getInterviewerTimeSlot().stream().filter(slot -> slot.getInterviewer().getId().equals(interviewerJoao.getId())).findFirst().orElseThrow();
        var slotsMatchedInterviewerPedro = result.getInterviewerTimeSlot().stream().filter(slot -> slot.getInterviewer().getId().equals(interviewerPedro.getId())).findFirst().orElseThrow();
        assertThat(result.getCandidate().getId(), is(candidate.getId()));
        assertThat(result.getInterviewerTimeSlot().size(), is(2));

        assertThat(slotsMatchedInterviewerJoao.getAvailableSlots().size(), is(1));
        assertThat(slotsMatchedInterviewerJoao.getAvailableSlots().get(0), is(slotMatchedJoao));

        assertThat(slotsMatchedInterviewerPedro.getAvailableSlots().size(), is(1));
        assertThat(slotsMatchedInterviewerPedro.getAvailableSlots().get(0), is(slotMatchedPedro));
    }


    @Test
    public void getAvailableLocalDateTimeTestForTheSameDay() {


        var a1 = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("10:00:00")).timeUntil(LocalTime.parse("15:59:59")).dateFrom(LocalDate.parse("2021-10-01")).dateUntil(LocalDate.parse("2021-10-01")).user(interviewer1).build();

        var availableTimeSlotList = List.of(a1);
        var test = testee.getAvailableLocalDateTime(availableTimeSlotList);

        var slot0 = LocalDateTime.parse("2021-10-01T10:00:00");
        var slot1 = LocalDateTime.parse("2021-10-01T11:00:00");
        var slot2 = LocalDateTime.parse("2021-10-01T12:00:00");
        var slot3 = LocalDateTime.parse("2021-10-01T13:00:00");
        var slot4 = LocalDateTime.parse("2021-10-01T14:00:00");
        var slot5 = LocalDateTime.parse("2021-10-01T15:00:00");

        assertThat(test.size(), is(6));
        assertThat(test.get(0).getDateTimeSlot(), is(slot0));
        assertThat(test.get(1).getDateTimeSlot(), is(slot1));
        assertThat(test.get(2).getDateTimeSlot(), is(slot2));
        assertThat(test.get(3).getDateTimeSlot(), is(slot3));
        assertThat(test.get(4).getDateTimeSlot(), is(slot4));
        assertThat(test.get(5).getDateTimeSlot(), is(slot5));
    }

    @Test
    public void getAvailableLocalDateTimeDifferentDays() {


        var a1 = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("10:00:00")).timeUntil(LocalTime.parse("15:59:59")).dateFrom(LocalDate.parse("2021-10-01")).dateUntil(LocalDate.parse("2021-10-02")).user(interviewer1).build();

        var availableTimeSlotList = List.of(a1);
        var test = testee.getAvailableLocalDateTime(availableTimeSlotList);

        var slot0 = LocalDateTime.parse("2021-10-01T10:00:00");
        var slot1 = LocalDateTime.parse("2021-10-01T11:00:00");
        var slot2 = LocalDateTime.parse("2021-10-01T12:00:00");
        var slot3 = LocalDateTime.parse("2021-10-01T13:00:00");
        var slot4 = LocalDateTime.parse("2021-10-01T14:00:00");
        var slot5 = LocalDateTime.parse("2021-10-01T15:00:00");

        var slot6 = LocalDateTime.parse("2021-10-02T10:00:00");
        var slot7 = LocalDateTime.parse("2021-10-02T11:00:00");
        var slot8 = LocalDateTime.parse("2021-10-02T12:00:00");
        var slot9 = LocalDateTime.parse("2021-10-02T13:00:00");
        var slot10 = LocalDateTime.parse("2021-10-02T14:00:00");
        var slot11 = LocalDateTime.parse("2021-10-02T15:00:00");

        assertThat(test.size(), is(12));
        assertThat(test.get(0).getDateTimeSlot(), is(slot0));
        assertThat(test.get(1).getDateTimeSlot(), is(slot1));
        assertThat(test.get(2).getDateTimeSlot(), is(slot2));
        assertThat(test.get(3).getDateTimeSlot(), is(slot3));
        assertThat(test.get(4).getDateTimeSlot(), is(slot4));
        assertThat(test.get(5).getDateTimeSlot(), is(slot5));
        assertThat(test.get(6).getDateTimeSlot(), is(slot6));
        assertThat(test.get(7).getDateTimeSlot(), is(slot7));
        assertThat(test.get(8).getDateTimeSlot(), is(slot8));
        assertThat(test.get(9).getDateTimeSlot(), is(slot9));
        assertThat(test.get(10).getDateTimeSlot(), is(slot10));
        assertThat(test.get(11).getDateTimeSlot(), is(slot11));
    }

    @Test
    public void getAvailableLocalDateTimeSameDayIntersectionHours() {

        var a1 = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("08:00:00")).timeUntil(LocalTime.parse("10:59:59")).dateFrom(LocalDate.parse("2021-10-01")).dateUntil(LocalDate.parse("2021-10-01")).user(interviewer1).build();
        var a2 = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("10:00:00")).timeUntil(LocalTime.parse("11:59:59")).dateFrom(LocalDate.parse("2021-10-01")).dateUntil(LocalDate.parse("2021-10-01")).user(interviewer1).build();

        var availableTimeSlotList = List.of(a1, a2);
        var test = testee.getAvailableLocalDateTime(availableTimeSlotList);

        var slot0 = LocalDateTime.parse("2021-10-01T08:00:00");
        var slot1 = LocalDateTime.parse("2021-10-01T09:00:00");
        var slot2 = LocalDateTime.parse("2021-10-01T10:00:00");
        var slot3 = LocalDateTime.parse("2021-10-01T11:00:00");

        assertThat(test.size(), is(4));
        assertThat(test.get(0).getDateTimeSlot(), is(slot0));
        assertThat(test.get(1).getDateTimeSlot(), is(slot1));
        assertThat(test.get(2).getDateTimeSlot(), is(slot2));
        assertThat(test.get(3).getDateTimeSlot(), is(slot3));
    }

    @Test
    public void getAvailableLocalDateTimeDifferentDateDifferentHours() {

        var a1 = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("08:00:00")).timeUntil(LocalTime.parse("10:59:59")).dateFrom(LocalDate.parse("2021-10-01")).dateUntil(LocalDate.parse("2021-10-02")).user(interviewer1).build();
        var a2 = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("15:00:00")).timeUntil(LocalTime.parse("16:59:59")).dateFrom(LocalDate.parse("2021-10-04")).dateUntil(LocalDate.parse("2021-10-05")).user(interviewer1).build();

        var availableTimeSlotList = List.of(a1, a2);
        var test = testee.getAvailableLocalDateTime(availableTimeSlotList);

        var slot0 = LocalDateTime.parse("2021-10-01T08:00:00");
        var slot1 = LocalDateTime.parse("2021-10-01T09:00:00");
        var slot2 = LocalDateTime.parse("2021-10-01T10:00:00");

        var slot3 = LocalDateTime.parse("2021-10-02T08:00:00");
        var slot4 = LocalDateTime.parse("2021-10-02T09:00:00");
        var slot5 = LocalDateTime.parse("2021-10-02T10:00:00");

        var slot6 = LocalDateTime.parse("2021-10-04T15:00:00");
        var slot7 = LocalDateTime.parse("2021-10-04T16:00:00");

        var slot8 = LocalDateTime.parse("2021-10-05T15:00:00");
        var slot9 = LocalDateTime.parse("2021-10-05T16:00:00");


        assertThat(test.size(), is(10));
        assertThat(test.get(0).getDateTimeSlot(), is(slot0));
        assertThat(test.get(1).getDateTimeSlot(), is(slot1));
        assertThat(test.get(2).getDateTimeSlot(), is(slot2));
        assertThat(test.get(3).getDateTimeSlot(), is(slot3));
        assertThat(test.get(4).getDateTimeSlot(), is(slot4));
        assertThat(test.get(5).getDateTimeSlot(), is(slot5));
        assertThat(test.get(6).getDateTimeSlot(), is(slot6));
        assertThat(test.get(7).getDateTimeSlot(), is(slot7));
        assertThat(test.get(8).getDateTimeSlot(), is(slot8));
        assertThat(test.get(9).getDateTimeSlot(), is(slot9));
    }

    @Test
    public void shouldReturnOneListWithAllAvailableTimeSlot() {

        var availableTimeSlot1 = AvailableTimeSlot.builder().id(1L).timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();
        var availableTimeSlot2 = AvailableTimeSlot.builder().id(2L).timeFrom(LocalTime.parse("15:00:00")).timeUntil(LocalTime.parse("16:00:00")).dateFrom(LocalDate.parse("2021-01-07")).dateUntil(LocalDate.parse("2021-02-10")).user(candidate).build();
        var availableTimeSlots = List.of(availableTimeSlot1, availableTimeSlot2);

        doReturn(availableTimeSlots).when(availableTimeSlotRepository).findAllByUserId(candidate.getId());

        var result = testee.findAllByUserId(1L);
        assertThat(result.size(), is(2));
        assertThat(result.contains(availableTimeSlot1), is(true));
        assertThat(result.contains(availableTimeSlot2), is(true));
    }

    @Test
    public void shouldReturnOneAvailableTimeSlot() {
        var availableTimeSlot1 = AvailableTimeSlot.builder().id(1L).timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();

        doReturn(Optional.of(availableTimeSlot1)).when(availableTimeSlotRepository).findById(availableTimeSlot1.getId());

        var result = testee.getById(availableTimeSlot1.getId());
        assertThat(result, is(availableTimeSlot1));
    }

    @Test
    public void getByIdShouldThrowsEntityNotFoundException() {
        var availableTimeSlot1 = AvailableTimeSlot.builder().id(1L).timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();

        doReturn(Optional.empty()).when(availableTimeSlotRepository).findById(availableTimeSlot1.getId());
        assertThrows(EntityNotFoundException.class, () -> testee.getById(availableTimeSlot1.getId()));

    }

    @Test
    public void shouldSaveAvailableSlot() {

        var availableTimeSlot1 = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();
        var availableTimeSlotCreated = AvailableTimeSlot.builder().id(1L).timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();

        doReturn(Collections.emptyList()).when(availableTimeSlotRepository).findAllByUserIdAndDateFromAndDateUntilAndTimeFromAndTimeUntil(any(), any(), any(), any(), any());

        doReturn(availableTimeSlotCreated).when(availableTimeSlotRepository).save(availableTimeSlot1);

        var result = testee.save(availableTimeSlot1);

        verify(userService, times(1)).getById(candidate.getId());
        assertThat(result.getId(), is(1L));
    }


    @Test
    public void saveShouldThrowsException() {

        var availableTimeSlot1 = AvailableTimeSlot.builder().timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();
        var availableTimeSlotDuplicated = AvailableTimeSlot.builder().id(1L).timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();
        doReturn(List.of(availableTimeSlotDuplicated)).when(availableTimeSlotRepository).findAllByUserIdAndDateFromAndDateUntilAndTimeFromAndTimeUntil(any(), any(), any(), any(), any());

        assertThrows(EntityExistsException.class, () -> testee.save(availableTimeSlot1));

        verify(userService, times(1)).getById(candidate.getId());
    }

    @Test
    public void shouldDeleteById() {
        var availableTimeSlot1 = AvailableTimeSlot.builder().id(1L).timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();
        doReturn(Optional.of(availableTimeSlot1)).when(availableTimeSlotRepository).findById(availableTimeSlot1.getId());
        testee.deleteById(availableTimeSlot1.getId());
    }

    @Test
    public void shouldUpdate() {

        var availableTimeSlot1 = AvailableTimeSlot.builder().id(1L).timeFrom(LocalTime.parse("21:00:00")).timeUntil(LocalTime.parse("22:00:00")).dateFrom(LocalDate.parse("2021-01-01")).dateUntil(LocalDate.parse("2021-02-02")).user(candidate).build();
        doReturn(Optional.of(availableTimeSlot1)).when(availableTimeSlotRepository).findById(availableTimeSlot1.getId());
        doReturn(List.of(availableTimeSlot1)).when(availableTimeSlotRepository).findAllByUserIdAndDateFromAndDateUntilAndTimeFromAndTimeUntil(any(), any(), any(), any(), any());
        doReturn(availableTimeSlot1).when(availableTimeSlotRepository).save(availableTimeSlot1);

        var result = testee.update(availableTimeSlot1.getId(), availableTimeSlot1);

        assertThat(result, is(availableTimeSlot1));
    }
}
