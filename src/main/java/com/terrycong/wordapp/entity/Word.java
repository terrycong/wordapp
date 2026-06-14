package com.terrycong.wordapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "word")
@Data
public class Word {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "textbook_id", nullable = false)
    private Long textbookId;

    @Column(nullable = false, length = 64)
    private String spelling;

    @Column(nullable = false, length = 255)
    private String meaning;

    @Column(length = 128)
    private String phonetic;

    @Column(name = "unit_no", nullable = false)
    private Integer unitNo = 1;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
}
