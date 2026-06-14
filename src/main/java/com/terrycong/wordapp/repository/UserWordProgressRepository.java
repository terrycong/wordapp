package com.terrycong.wordapp.repository;

import com.terrycong.wordapp.entity.UserWordProgress;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserWordProgressRepository extends JpaRepository<UserWordProgress, Long> {
    Optional<UserWordProgress> findByUserIdAndWordId(Long userId, Long wordId);

    List<UserWordProgress> findByUserId(Long userId);

    @Query("SELECT p FROM UserWordProgress p WHERE p.userId = :uid AND p.mastered = false " +
           "AND p.nextReviewAt <= :now ORDER BY p.nextReviewAt ASC")
    List<UserWordProgress> findDue(@Param("uid") Long uid,
                                   @Param("now") LocalDateTime now,
                                   Pageable pageable);

    long countByUserIdAndMasteredFalseAndNextReviewAtLessThanEqual(Long userId, LocalDateTime time);

    long countByUserIdAndMasteredTrue(Long userId);

    long countByUserId(Long userId);

    @Query("SELECT p FROM UserWordProgress p WHERE p.userId = :uid AND p.wrongCount > 0 " +
           "ORDER BY p.wrongCount DESC")
    List<UserWordProgress> findMostWrong(@Param("uid") Long uid, Pageable pageable);
}
