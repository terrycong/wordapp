package com.terrycong.wordapp.controller;

import com.terrycong.wordapp.entity.User;
import com.terrycong.wordapp.entity.UserTextbook;
import com.terrycong.wordapp.entity.UserWordProgress;
import com.terrycong.wordapp.entity.Word;
import com.terrycong.wordapp.repository.TextbookRepository;
import com.terrycong.wordapp.repository.UserWordProgressRepository;
import com.terrycong.wordapp.repository.WordRepository;
import com.terrycong.wordapp.service.EbbinghausService;
import com.terrycong.wordapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final EbbinghausService ebbinghausService;
    private final TextbookRepository textbookRepo;
    private final WordRepository wordRepo;
    private final UserWordProgressRepository progressRepo;
    private final com.terrycong.wordapp.repository.ReviewLogRepository reviewLogRepo;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails ud, Model model) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        long total = ebbinghausService.countTotal(u.getId());
        long mastered = ebbinghausService.countMastered(u.getId());
        long due = ebbinghausService.countDue(u.getId());

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        long todayDone = reviewLogRepo.countByUserIdAndAnsweredAtBetween(u.getId(), startOfDay, endOfDay);
        long todayCorrect = reviewLogRepo.countByUserIdAndCorrectAndAnsweredAtBetween(u.getId(), true, startOfDay, endOfDay);
        long todayWrong = reviewLogRepo.countByUserIdAndCorrectAndAnsweredAtBetween(u.getId(), false, startOfDay, endOfDay);

        long totalWrong = reviewLogRepo.countWrongByUser(u.getId());
        long totalReview = reviewLogRepo.countByUser(u.getId());

        model.addAttribute("user", u);
        model.addAttribute("total", total);
        model.addAttribute("mastered", mastered);
        model.addAttribute("due", due);
        model.addAttribute("todayDone", todayDone);
        model.addAttribute("todayCorrect", todayCorrect);
        model.addAttribute("todayWrong", todayWrong);
        model.addAttribute("totalWrong", totalWrong);
        model.addAttribute("totalReview", totalReview);
        model.addAttribute("dailyGoal", u.getDailyGoal());
        return "home";
    }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String registerForm() { return "register"; }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(required = false) String displayName,
                           Model model) {
        try {
            userService.register(username, password, displayName);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        return "redirect:/login?registered";
    }

    @GetMapping("/textbooks")
    public String textbooks(@AuthenticationPrincipal UserDetails ud, Model model) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        Set<Long> selected = new HashSet<>();
        for (UserTextbook ut : userService.userTextbooks(u.getId())) selected.add(ut.getTextbookId());
        model.addAttribute("textbooks", textbookRepo.findAllByOrderBySortOrderAsc());
        model.addAttribute("selected", selected);
        return "textbooks";
    }

    @PostMapping("/textbooks/add")
    public String addTextbook(@AuthenticationPrincipal UserDetails ud, @RequestParam Long textbookId) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        userService.addTextbook(u.getId(), textbookId);
        return "redirect:/textbooks";
    }

    @PostMapping("/textbooks/remove")
    public String removeTextbook(@AuthenticationPrincipal UserDetails ud, @RequestParam Long textbookId) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        userService.removeTextbook(u.getId(), textbookId);
        return "redirect:/textbooks";
    }

    @PostMapping("/settings/daily-goal")
    public String updateGoal(@AuthenticationPrincipal UserDetails ud, @RequestParam Integer dailyGoal) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        userService.updateDailyGoal(u.getId(), dailyGoal);
        return "redirect:/";
    }

    /** 错题统计页 */
    @GetMapping("/stats")
    public String stats(@AuthenticationPrincipal UserDetails ud, Model model) {
        User u = userService.findByUsername(ud.getUsername()).orElseThrow();
        List<UserWordProgress> mostWrong = progressRepo.findMostWrong(u.getId(), PageRequest.of(0, 50));
        Map<Long, Word> wordMap = new HashMap<>();
        for (UserWordProgress p : mostWrong) {
            wordRepo.findById(p.getWordId()).ifPresent(w -> wordMap.put(w.getId(), w));
        }
        model.addAttribute("rows", mostWrong);
        model.addAttribute("wordMap", wordMap);
        model.addAttribute("totalWrong", reviewLogRepo.countWrongByUser(u.getId()));
        model.addAttribute("totalReview", reviewLogRepo.countByUser(u.getId()));
        return "stats";
    }
}
