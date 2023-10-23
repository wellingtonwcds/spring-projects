package com.tamanna.interviewcalendar.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "system_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(of = {"id", "name", "roleType"})
@EqualsAndHashCode(of = {"id", "name", "roleType"}, callSuper = false)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_user_seq")
    @SequenceGenerator(name = "system_user_seq", sequenceName = "system_user_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    @Type(type = "com.tamanna.interviewcalendar.util.EnumTypeSql")
    private RoleType roleType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableTimeSlot> availableTimeSlotList;


}
