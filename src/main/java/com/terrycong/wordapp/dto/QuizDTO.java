package com.terrycong.wordapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizDTO {
    private Long wordId;
    /** CN_TO_EN, EN_TO_CN, SPELL, LISTEN_CHOICE */
    private String type;
    private String question;
    private String spelling;
    private String meaning;
    private String phonetic;
    private List<String> options;
    private String answer;
}
