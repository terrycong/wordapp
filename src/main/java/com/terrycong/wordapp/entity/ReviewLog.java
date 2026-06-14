package com.terrycong.wordapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_log")
@Data
public class ReviewLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "word_id", nullable = false)
    private Long wordId;

    /** CN_TO_EN / EN_TO_CN / SPELL / LISTEN */
    @Column(name = "quiz_type", nullable = false, length = 32)
    private String quizType;

    @Column(nullable = false)
    private Boolean correct;

    @Column(name = "user_answer", length = 255)
    private String userAnswer;

    @Column(name = "answered_at", nullable = false)
    private LocalDateTime answeredAt = LocalDateTime.now();
}
