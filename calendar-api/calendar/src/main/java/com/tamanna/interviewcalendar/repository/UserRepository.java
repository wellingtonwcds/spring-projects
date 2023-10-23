package com.tamanna.interviewcalendar.repository;

import com.tamanna.interviewcalendar.domain.RoleType;
import com.tamanna.interviewcalendar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<List<User>> findByNameAndRoleType(String name, RoleType roleType);

    Optional<List<User>> findByRoleType(RoleType roleType);
}
