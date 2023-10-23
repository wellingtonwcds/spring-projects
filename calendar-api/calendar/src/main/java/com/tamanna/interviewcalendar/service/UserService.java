package com.tamanna.interviewcalendar.service;

import com.tamanna.interviewcalendar.domain.RoleType;
import com.tamanna.interviewcalendar.domain.User;
import com.tamanna.interviewcalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public List<User> findAllByRoleType(RoleType roleType) {
        return repository.findByRoleType(roleType).orElseThrow();
    }

    public User update(Long id, User user) {
        checkDuplicated(user, id);
        User userPersisted = getById(id);
        userPersisted.setName(user.getName());
        userPersisted.setRoleType(user.getRoleType());
        return repository.save(userPersisted);
    }

    public User getById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User was not found with id: %d", userId)));
    }

    public User save(User user) {
        checkDuplicated(user);
        return repository.save(user);
    }

    public void deleteById(Long userId) {
        User user = getById(userId);
        repository.delete(user);
    }

    public void checkDuplicated(User user) {
        checkDuplicated(user, null);
    }

    public void checkDuplicated(User user, Long idToIgnore) {
        List<User> users = repository.findByNameAndRoleType(user.getName(), user.getRoleType()).orElse(Collections.emptyList());
        if (users.size() > 0 && !users.get(0).getId().equals(idToIgnore)) {
            throw new EntityExistsException("There is another user with the same attributes");
        }
    }
}
