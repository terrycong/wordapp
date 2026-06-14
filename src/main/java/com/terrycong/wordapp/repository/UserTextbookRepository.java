package com.terrycong.wordapp.repository;

import com.terrycong.wordapp.entity.UserTextbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserTextbookRepository extends JpaRepository<UserTextbook, Long> {
    List<UserTextbook> findByUserId(Long userId);
    boolean existsByUserIdAndTextbookId(Long userId, Long textbookId);

    @Transactional
    void deleteByUserIdAndTextbookId(Long userId, Long textbookId);
}
