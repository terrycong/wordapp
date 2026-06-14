package com.terrycong.wordapp.service;

import com.terrycong.wordapp.entity.*;
import com.terrycong.wordapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTextbookRepository userTextbookRepo;
    private final EbbinghausService ebbinghausService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User register(String username, String password, String displayName) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("用户名已被占用");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setDisplayName(displayName == null || displayName.isBlank() ? username : displayName);
        u.setRole("USER");
        u.setDailyGoal(20);
        u.setEnabled(true);
        return userRepository.save(u);
    }

    @Transactional
    public void updateDailyGoal(Long userId, Integer goal) {
        User u = userRepository.findById(userId).orElseThrow();
        if (goal == null || goal < 1) goal = 1;
        if (goal > 200) goal = 200;
        u.setDailyGoal(goal);
        userRepository.save(u);
    }

    @Transactional
    public void addTextbook(Long userId, Long textbookId) {
        if (!userTextbookRepo.existsByUserIdAndTextbookId(userId, textbookId)) {
            UserTextbook ut = new UserTextbook();
            ut.setUserId(userId);
            ut.setTextbookId(textbookId);
            userTextbookRepo.save(ut);
        }
        ebbinghausService.initProgressForTextbook(userId, textbookId);
    }

    @Transactional
    public void removeTextbook(Long userId, Long textbookId) {
        userTextbookRepo.deleteByUserIdAndTextbookId(userId, textbookId);
    }

    public List<UserTextbook> userTextbooks(Long userId) {
        return userTextbookRepo.findByUserId(userId);
    }
}
