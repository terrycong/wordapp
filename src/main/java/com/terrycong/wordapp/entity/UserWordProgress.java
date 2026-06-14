package com.terrycong.wordapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_word_progress")
@Data
public class UserWordProgress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "word_id", nullable = false)
    private Long wordId;

    /** 当前阶段 0~6, 对应 1d/2d/4d/7d/15d/21d/30d 节点; >=7 视为掌握 */
    @Column(nullable = false)
    private Integer stage = 0;

    @Column(name = "next_review_at", nullable = false)
    private LocalDateTime nextReviewAt = LocalDateTime.now();

    @Column(name = "last_review_at")
    private LocalDateTime lastReviewAt;

    @Column(name = "correct_count", nullable = false)
    private Integer correctCount = 0;

    @Column(name = "wrong_count", nullable = false)
    private Integer wrongCount = 0;

    @Column(name = "consecutive_correct", nullable = false)
    private Integer consecutiveCorrect = 0;

    @Column(nullable = false)
    private Boolean mastered = false;
}
