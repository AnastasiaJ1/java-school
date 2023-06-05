package com.digdes.school.project.model;

import com.digdes.school.project.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Proxy(lazy = false)
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
