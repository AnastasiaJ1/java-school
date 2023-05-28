package com.digdes.school.project.model;

import com.digdes.school.project.enums.EmployeeStatus;
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
    @Column(name = "id")
    private UUID id;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "surname")
    private String surname;
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "account")
    private String account;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;


}
