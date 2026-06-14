package com.terrycong.wordapp.repository;

import com.terrycong.wordapp.entity.ReviewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReviewLogRepository extends JpaRepository<ReviewLog, Long> {

    long countByUserIdAndAnsweredAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    long countByUserIdAndCorrectAndAnsweredAtBetween(Long userId, Boolean correct,
                                                     LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(r) FROM ReviewLog r WHERE r.userId = :uid AND r.correct = false")
    long countWrongByUser(@Param("uid") Long uid);

    @Query("SELECT COUNT(r) FROM ReviewLog r WHERE r.userId = :uid")
    long countByUser(@Param("uid") Long uid);
}
