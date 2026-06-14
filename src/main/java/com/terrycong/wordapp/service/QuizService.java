package com.terrycong.wordapp.service;

import com.terrycong.wordapp.dto.QuizDTO;
import com.terrycong.wordapp.entity.UserWordProgress;
import com.terrycong.wordapp.entity.Word;
import com.terrycong.wordapp.repository.UserTextbookRepository;
import com.terrycong.wordapp.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 出题服务: 根据当前到期单词生成题目
 * 题型: CN_TO_EN(中→英选择), EN_TO_CN(英→中选择), SPELL(拼写), LISTEN(听音选义/拼写)
 */
@Service
@RequiredArgsConstructor
public class QuizService {

    private final EbbinghausService ebbinghausService;
    private final WordRepository wordRepository;
    private final UserTextbookRepository userTextbookRepo;

    private static final String[] CHOICE_TYPES = {"CN_TO_EN", "EN_TO_CN", "LISTEN_CHOICE"};
    private static final Random RNG = new Random();

    /**
     * 取一道题: 优先取到期, 不足则随机从用户已选课本里补.
     */
    public QuizDTO nextQuiz(Long userId, String forcedType) {
        // 1. 选词
        List<UserWordProgress> due = ebbinghausService.getDueProgress(userId, 50);
        Word target;
        if (!due.isEmpty()) {
            UserWordProgress p = due.get(RNG.nextInt(due.size()));
            target = wordRepository.findById(p.getWordId()).orElse(null);
        } else {
            target = pickRandomFromUserTextbooks(userId);
        }
        if (target == null) return null;

        // 2. 决定题型
        String type;
        if (forcedType != null && !forcedType.isBlank()) {
            type = forcedType;
        } else {
            // 拼写权重 ~ 25%, 其余三种各 25%
            int r = RNG.nextInt(4);
            if (r == 3) type = "SPELL";
            else type = CHOICE_TYPES[r];
        }

        // 3. 构造题
        QuizDTO q = new QuizDTO();
        q.setWordId(target.getId());
        q.setType(type);
        q.setSpelling(target.getSpelling());
        q.setMeaning(target.getMeaning());
        q.setPhonetic(target.getPhonetic());

        switch (type) {
            case "CN_TO_EN" -> {
                q.setQuestion("「" + target.getMeaning() + "」对应的英文是？");
                q.setOptions(buildChoices(userId, target, true));
                q.setAnswer(target.getSpelling());
            }
            case "EN_TO_CN" -> {
                q.setQuestion("\"" + target.getSpelling() + "\" 是什么意思？");
                q.setOptions(buildChoices(userId, target, false));
                q.setAnswer(target.getMeaning());
            }
            case "SPELL" -> {
                q.setQuestion("根据释义拼写单词: " + target.getMeaning());
                q.setAnswer(target.getSpelling());
            }
            case "LISTEN_CHOICE" -> {
                q.setQuestion("听音选意思（点 🔊 播放）");
                q.setOptions(buildChoices(userId, target, false));
                q.setAnswer(target.getMeaning());
            }
            default -> {
                q.setQuestion("\"" + target.getSpelling() + "\" 是什么意思？");
                q.setOptions(buildChoices(userId, target, false));
                q.setAnswer(target.getMeaning());
            }
        }
        return q;
    }

    /**
     * 校验答案. 拼写忽略大小写和首尾空格; 选择题精确匹配文本.
     */
    public boolean checkAnswer(QuizDTO q, String userAnswer) {
        if (userAnswer == null) return false;
        String a = userAnswer.trim();
        String correct = q.getAnswer() == null ? "" : q.getAnswer().trim();
        if ("SPELL".equals(q.getType())) {
            return correct.equalsIgnoreCase(a);
        }
        return correct.equals(a);
    }

    private Word pickRandomFromUserTextbooks(Long userId) {
        List<Long> tbIds = userTextbookRepo.findByUserId(userId).stream()
                .map(t -> t.getTextbookId()).toList();
        if (tbIds.isEmpty()) return null;
        List<Word> all = wordRepository.findByTextbookIdInOrderBySortOrderAsc(tbIds);
        if (all.isEmpty()) return null;
        return all.get(RNG.nextInt(all.size()));
    }

    private List<String> buildChoices(Long userId, Word target, boolean useSpellingAsOption) {
        List<Long> tbIds = userTextbookRepo.findByUserId(userId).stream()
                .map(t -> t.getTextbookId()).toList();
        List<Word> pool = tbIds.isEmpty()
                ? wordRepository.findAll()
                : wordRepository.findByTextbookIdInOrderBySortOrderAsc(tbIds);
        // 排除自身
        List<Word> distractors = pool.stream()
                .filter(w -> !Objects.equals(w.getId(), target.getId()))
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(distractors, RNG);

        Set<String> opts = new LinkedHashSet<>();
        String correctOpt = useSpellingAsOption ? target.getSpelling() : target.getMeaning();
        opts.add(correctOpt);
        for (Word w : distractors) {
            if (opts.size() >= 4) break;
            String o = useSpellingAsOption ? w.getSpelling() : w.getMeaning();
            if (o != null && !o.equals(correctOpt)) opts.add(o);
        }
        List<String> out = new ArrayList<>(opts);
        Collections.shuffle(out, RNG);
        return out;
    }
}
