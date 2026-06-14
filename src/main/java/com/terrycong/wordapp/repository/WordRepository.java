package com.terrycong.wordapp.repository;

import com.terrycong.wordapp.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByTextbookIdOrderBySortOrderAsc(Long textbookId);
    List<Word> findByTextbookIdInOrderBySortOrderAsc(List<Long> textbookIds);
    long countByTextbookId(Long textbookId);
}
