package com.tamanna.interviewcalendar.service;

import com.tamanna.interviewcalendar.domain.RoleType;
import com.tamanna.interviewcalendar.domain.User;
import com.tamanna.interviewcalendar.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService testee;

    @Mock
    private UserRepository repository;

    private User candidate;
    private User interviewer1;

    @BeforeEach
    public void setUp() {
        candidate = User.builder().id(1L).name("Candidate").roleType(RoleType.CANDIDATE).build();
        interviewer1 = User.builder().name("wellington").id(2L).roleType(RoleType.INTERVIEWER).build();
    }

    @Test
    public void shouldFindAllUsers() {

        doReturn(List.of(candidate, interviewer1)).when(repository).findAll();
        var result = testee.findAll();

        assertThat(result.size(), is(2));
        assertThat(result.contains(candidate), is(true));
        assertThat(result.contains(interviewer1), is(true));
    }

    @Test
    public void shouldFindAllByRoleType() {

        doReturn(Optional.of(List.of(candidate))).when(repository).findByRoleType(candidate.getRoleType());
        var result = testee.findAllByRoleType(candidate.getRoleType());

        assertThat(result.size(), is(1));
        assertThat(result.contains(candidate), is(true));
    }

    @Test
    public void shouldUpdateUser() {
        doReturn(Optional.of(List.of(candidate))).when(repository).findByNameAndRoleType(candidate.getName(), candidate.getRoleType());
        doReturn(Optional.of(candidate)).when(repository).findById(candidate.getId());
        doReturn(candidate).when(repository).save(candidate);
        var result = testee.update(candidate.getId(), candidate);

        assertThat(result, is(candidate));
    }

    @Test
    public void shouldThrowsEntityExistsException() {
        doReturn(Optional.of(List.of(interviewer1))).when(repository).findByNameAndRoleType(candidate.getName(), candidate.getRoleType());
        assertThrows(EntityExistsException.class, () -> testee.update(candidate.getId(), candidate));
    }

    @Test
    public void shouldGetUserById() {
        doReturn(Optional.of(candidate)).when(repository).findById(candidate.getId());
        var result = testee.getById(candidate.getId());

        assertThat(result, is(candidate));
    }

    @Test
    public void shouldThrowException() {
        doReturn(Optional.empty()).when(repository).findById(candidate.getId());
        assertThrows(EntityNotFoundException.class, () -> testee.getById(candidate.getId()));
    }

    @Test
    public void shouldSaveUser() {
        var userToBeCreated = User.builder().name("Candidate").roleType(RoleType.CANDIDATE).build();
        doReturn(Optional.empty()).when(repository).findByNameAndRoleType(candidate.getName(), candidate.getRoleType());
        doReturn(candidate).when(repository).save(userToBeCreated);
        var result = testee.save(userToBeCreated);

        assertThat(result, is(candidate));
    }

    @Test
    public void shouldSaveUserShouldThrowsException() {
        var userToBeCreated = User.builder().name("Candidate").roleType(RoleType.CANDIDATE).build();
        doReturn(Optional.of(List.of(interviewer1))).when(repository).findByNameAndRoleType(candidate.getName(), candidate.getRoleType());
        assertThrows(EntityExistsException.class, () -> testee.save(userToBeCreated));
    }
}
