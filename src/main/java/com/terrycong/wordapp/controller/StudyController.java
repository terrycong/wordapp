package com.terrycong.wordapp.controller;

import com.terrycong.wordapp.dto.QuizDTO;
import com.terrycong.wordapp.entity.User;
import com.terrycong.wordapp.service.EbbinghausService;
import com.terrycong.wordapp.service.QuizService;
import com.terrycong.wordapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final QuizService quizService;
    private final EbbinghausService ebbinghausService;
    private final UserService userService;

    private static final String SESSION_KEY = "currentQuiz";

    @GetMapping
    public String studyPage(@AuthenticationPrincipal UserDetails ud, Model model,
                            @RequestParam(required = false) String type) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        if (userService.userTextbooks(u.getId()).isEmpty()) {
            return "redirect:/textbooks?empty";
        }
        model.addAttribute("forcedType", type);
        return "study";
    }

    @GetMapping("/api/next")
    @ResponseBody
    public Map<String, Object> next(@AuthenticationPrincipal UserDetails ud,
                                    @RequestParam(required = false) String type,
                                    HttpSession session) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        QuizDTO q = quizService.nextQuiz(u.getId(), type);
        Map<String, Object> resp = new HashMap<>();
        if (q == null) {
            resp.put("ok", false);
            resp.put("error", "暂无单词。请先选择课本。");
            return resp;
        }
        session.setAttribute(SESSION_KEY, q);
        resp.put("ok", true);
        resp.put("type", q.getType());
        resp.put("question", q.getQuestion());
        resp.put("options", q.getOptions());
        resp.put("spelling", q.getSpelling());
        resp.put("phonetic", q.getPhonetic());
        resp.put("wordId", q.getWordId());
        return resp;
    }

    @PostMapping("/api/answer")
    @ResponseBody
    public Map<String, Object> answer(@AuthenticationPrincipal UserDetails ud,
                                      @RequestParam String userAnswer,
                                      HttpSession session) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        QuizDTO q = (QuizDTO) session.getAttribute(SESSION_KEY);
        Map<String, Object> resp = new HashMap<>();
        if (q == null) {
            resp.put("ok", false);
            resp.put("error", "题目已过期，请刷新");
            return resp;
        }
        boolean correct = quizService.checkAnswer(q, userAnswer);
        ebbinghausService.recordAnswer(u.getId(), q.getWordId(), q.getType(), correct, userAnswer);
        session.removeAttribute(SESSION_KEY);

        resp.put("ok", true);
        resp.put("correct", correct);
        resp.put("answer", q.getAnswer());
        resp.put("spelling", q.getSpelling());
        resp.put("meaning", q.getMeaning());
        resp.put("phonetic", q.getPhonetic());
        return resp;
    }
}
