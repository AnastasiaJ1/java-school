package com.digdes.school.project.model;

import com.digdes.school.project.model.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Proxy(lazy = false)
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    private UUID id;
    @Column
    private String lastname;
    @Column
    private String firstname;
    @Column
    private String surname;
    @Column
    private String jobTitle;
    @Column
    private String account;
    @Column
    private String email;
    @Column
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;


}
