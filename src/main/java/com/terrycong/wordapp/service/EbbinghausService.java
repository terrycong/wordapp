package com.terrycong.wordapp.service;

import com.terrycong.wordapp.entity.*;
import com.terrycong.wordapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 艾宾浩斯调度服务
 * 节点(天): 1, 2, 4, 7, 15, 21, 30
 * 答对: stage++ 推进到下一个节点；stage>=节点数 → mastered=true
 * 答错: stage 重置为 0, 1天后再复习
 */
@Service
@RequiredArgsConstructor
public class EbbinghausService {

    /** 复习间隔, 单位: 天 */
    public static final int[] INTERVAL_DAYS = {1, 2, 4, 7, 15, 21, 30};

    private final UserWordProgressRepository progressRepo;
    private final UserTextbookRepository userTextbookRepo;
    private final WordRepository wordRepo;
    private final ReviewLogRepository reviewLogRepo;

    /**
     * 用户加入新课本时, 为该课本所有单词初始化进度记录(立即可学).
     */
    @Transactional
    public int initProgressForTextbook(Long userId, Long textbookId) {
        List<Word> words = wordRepo.findByTextbookIdOrderBySortOrderAsc(textbookId);
        int created = 0;
        LocalDateTime now = LocalDateTime.now();
        for (Word w : words) {
            if (progressRepo.findByUserIdAndWordId(userId, w.getId()).isEmpty()) {
                UserWordProgress p = new UserWordProgress();
                p.setUserId(userId);
                p.setWordId(w.getId());
                p.setStage(0);
                p.setNextReviewAt(now); // 立即可学
                progressRepo.save(p);
                created++;
            }
        }
        return created;
    }

    /**
     * 处理一次答题结果, 更新调度.
     */
    @Transactional
    public void recordAnswer(Long userId, Long wordId, String quizType, boolean correct, String userAnswer) {
        UserWordProgress p = progressRepo.findByUserIdAndWordId(userId, wordId)
                .orElseGet(() -> {
                    UserWordProgress np = new UserWordProgress();
                    np.setUserId(userId);
                    np.setWordId(wordId);
                    return np;
                });

        LocalDateTime now = LocalDateTime.now();
        p.setLastReviewAt(now);

        if (correct) {
            p.setCorrectCount(p.getCorrectCount() + 1);
            p.setConsecutiveCorrect(p.getConsecutiveCorrect() + 1);
            int newStage = p.getStage() + 1;
            if (newStage >= INTERVAL_DAYS.length) {
                p.setMastered(true);
                p.setStage(INTERVAL_DAYS.length);
                p.setNextReviewAt(now.plusDays(365)); // 视为掌握, 远期不再排
            } else {
                p.setStage(newStage);
                p.setNextReviewAt(now.plusDays(INTERVAL_DAYS[newStage]));
            }
        } else {
            // 错了就重置
            p.setWrongCount(p.getWrongCount() + 1);
            p.setConsecutiveCorrect(0);
            p.setStage(0);
            p.setMastered(false);
            p.setNextReviewAt(now.plusDays(INTERVAL_DAYS[0]).minusDays(1)); // 错完立即可重做(now+0d内)
            // 实际就让 next_review_at = now, 立即可再练
            p.setNextReviewAt(now);
        }
        progressRepo.save(p);

        // 记录日志
        ReviewLog log = new ReviewLog();
        log.setUserId(userId);
        log.setWordId(wordId);
        log.setQuizType(quizType);
        log.setCorrect(correct);
        log.setUserAnswer(userAnswer);
        log.setAnsweredAt(now);
        reviewLogRepo.save(log);
    }

    /**
     * 取出当前到期(应该复习)的单词. 限制 limit.
     * 如果到期不够, 不补充新词(由调用方处理).
     */
    public List<UserWordProgress> getDueProgress(Long userId, int limit) {
        return progressRepo.findDue(userId, LocalDateTime.now(), PageRequest.of(0, Math.max(1, limit)));
    }

    public long countDue(Long userId) {
        return progressRepo.countByUserIdAndMasteredFalseAndNextReviewAtLessThanEqual(userId, LocalDateTime.now());
    }

    public long countTotal(Long userId) {
        return progressRepo.countByUserId(userId);
    }

    public long countMastered(Long userId) {
        return progressRepo.countByUserIdAndMasteredTrue(userId);
    }
}
