--liquibase formatted sql

--changeset wordapp:001-schema
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(64),
    role VARCHAR(32) NOT NULL DEFAULT 'USER',
    daily_goal INT NOT NULL DEFAULT 20,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS textbook (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(64) NOT NULL UNIQUE,
    name VARCHAR(128) NOT NULL,
    grade INT NOT NULL,
    term INT NOT NULL,
    publisher VARCHAR(64) NOT NULL DEFAULT '人教版PEP',
    sort_order INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS word (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    textbook_id BIGINT NOT NULL,
    spelling VARCHAR(64) NOT NULL,
    meaning VARCHAR(255) NOT NULL,
    phonetic VARCHAR(128),
    unit_no INT NOT NULL DEFAULT 1,
    sort_order INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_word_textbook FOREIGN KEY (textbook_id) REFERENCES textbook(id)
);
CREATE INDEX idx_word_textbook ON word(textbook_id);

-- 用户的学习计划：选了哪些课本
CREATE TABLE IF NOT EXISTS user_textbook (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    textbook_id BIGINT NOT NULL,
    added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_user_textbook UNIQUE (user_id, textbook_id)
);

-- 用户每个单词的学习进度
CREATE TABLE IF NOT EXISTS user_word_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    stage INT NOT NULL DEFAULT 0,
    next_review_at TIMESTAMP NOT NULL,
    last_review_at TIMESTAMP,
    correct_count INT NOT NULL DEFAULT 0,
    wrong_count INT NOT NULL DEFAULT 0,
    consecutive_correct INT NOT NULL DEFAULT 0,
    mastered BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_user_word UNIQUE (user_id, word_id)
);
CREATE INDEX idx_uwp_user_next ON user_word_progress(user_id, next_review_at);

-- 答题记录
CREATE TABLE IF NOT EXISTS review_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    word_id BIGINT NOT NULL,
    quiz_type VARCHAR(32) NOT NULL,
    correct BOOLEAN NOT NULL,
    user_answer VARCHAR(255),
    answered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_rl_user ON review_log(user_id, answered_at);

-- 默认管理员账号 (密码: admin123，BCrypt)
INSERT INTO users (username, password, display_name, role, daily_goal)
VALUES ('admin', '$2a$10$202bjwR4Zak1ks9mCEd3rOusoREc.B4jXAbRhM15ELXmCcKW2GsJ2', '管理员', 'ADMIN', 20);
