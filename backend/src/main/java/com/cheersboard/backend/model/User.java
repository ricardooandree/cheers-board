package com.cheersboard.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 8, max = 50)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(min = 8, max = 150)
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Pin> pins;

    @OneToMany(mappedBy = "user")
    private List<Like> likes;
}
