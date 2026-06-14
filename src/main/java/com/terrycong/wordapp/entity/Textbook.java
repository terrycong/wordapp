package com.terrycong.wordapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "textbook")
@Data
public class Textbook {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String code;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private Integer term;

    @Column(nullable = false, length = 64)
    private String publisher;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
}
