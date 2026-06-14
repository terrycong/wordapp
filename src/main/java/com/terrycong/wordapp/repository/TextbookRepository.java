package com.terrycong.wordapp.repository;

import com.terrycong.wordapp.entity.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextbookRepository extends JpaRepository<Textbook, Long> {
    List<Textbook> findAllByOrderBySortOrderAsc();
}
