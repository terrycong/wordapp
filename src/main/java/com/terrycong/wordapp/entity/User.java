package com.terrycong.wordapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_name", length = 64)
    private String displayName;

    @Column(nullable = false, length = 32)
    private String role = "USER";

    @Column(name = "daily_goal", nullable = false)
    private Integer dailyGoal = 20;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean enabled = true;
}
