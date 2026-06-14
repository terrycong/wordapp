# 单词大冒险 (wordapp)

> Spring Boot + Thymeleaf + Bootstrap + 艾宾浩斯遗忘曲线的小学英语背单词系统。

## ✨ 功能

- 🔐 **多用户登录** (Spring Security + BCrypt)，开箱即用账号 `admin / admin123`
- 📚 **人教版 PEP 1-6 年级** 12 册课本词库共 **404 个单词**（按学期/单元组织）
- 🧠 **艾宾浩斯遗忘曲线**: 节点 1d / 2d / 4d / 7d / 15d / 21d / 30d，**错了重置到第 0 阶段**，进 7 阶段视为掌握
- 🎯 **可调整每日目标**（1-200/天）
- 📝 **混合题型**:
  - 中→英 选择题 (4选1)
  - 英→中 选择题 (4选1)
  - 看意思·拼写 (输入框)
  - 听音·选意思 (TTS 朗读 + 4选1)
- 🔊 **听写功能** 浏览器 Web Speech API，零成本 TTS
- 📊 **错题统计** 累计错题 + Top 50 最常错单词
- 🗂️ **按学期/年级选课本** 12 册任意组合，支持加入和移除

## 🛠️ 技术栈

- Spring Boot 3.3.4 + Java 17
- Spring Data JPA + Spring Security
- Thymeleaf + Bootstrap 5.3 + Bootstrap Icons
- Liquibase 数据库版本管理
- **H2** (默认，文件数据库) / **MySQL** (预留 profile)

## 🚀 运行

### 🐳 Docker (推荐)

```bash
# 拉取或本地构建
docker build -t ghcr.io/terrycong/wordapp:1.0.0-local .

# 启动 (端口 38087, 数据持久化到 host 目录)
docker run -d --name wordapp --restart unless-stopped \
  -p 38087:8080 \
  -v $PWD/wordapp-data:/app/data \
  -e TZ=Asia/Shanghai \
  ghcr.io/terrycong/wordapp:1.0.0-local

# 验证
curl http://localhost:38087/actuator/health
```

打开 <http://localhost:38087>，用 `admin / admin123` 登录或注册新账号。

> ⚠️ 容器内运行用户为 `spring` (uid 65532)，挂载目录需要可写权限：`chmod 777 wordapp-data` 或 `chown 65532:65532`。

### 开发模式 (H2)

```bash
mvn -DskipTests package
java -jar target/wordapp.jar
```

打开 http://localhost:8080，用 `admin / admin123` 登录或者注册新账号。

数据保存在 `./data/wordapp.mv.db`，删除即可重置。
H2 控制台: http://localhost:8080/h2-console (jdbc URL: `jdbc:h2:file:./data/wordapp;MODE=MySQL`)

### 切换到 MySQL

```bash
java -jar target/wordapp.jar --spring.profiles.active=mysql \
  -DMYSQL_URL="jdbc:mysql://localhost:3306/wordapp?serverTimezone=Asia/Shanghai" \
  -DMYSQL_USER=wordapp -DMYSQL_PASSWORD=wordapp
```

## 📂 项目结构

```
wordapp/
├── pom.xml
├── scripts/
│   ├── words_data.json     # 词表数据源 (易于扩展)
│   └── gen_words.py        # 把 JSON 转成 Liquibase SQL
├── src/main/java/com/terrycong/wordapp/
│   ├── WordappApplication.java
│   ├── config/SecurityConfig.java
│   ├── controller/         # HomeController / StudyController / BuildInfoAdvice
│   ├── entity/             # User / Textbook / Word / UserTextbook / UserWordProgress / ReviewLog
│   ├── repository/         # 6 个 JPA Repository
│   ├── service/
│   │   ├── EbbinghausService.java   # 艾宾浩斯调度核心
│   │   ├── QuizService.java         # 出题 + 判分
│   │   └── UserService.java
│   ├── dto/QuizDTO.java
│   └── security/CustomUserDetailsService.java
└── src/main/resources/
    ├── application.properties (+h2/+mysql)
    ├── db/changelog/         # 5 个 SQL changeset (建表+课本+词表)
    ├── static/css/app.css
    └── templates/            # login/register/home/textbooks/study/stats + fragments/nav
```

## 📝 扩展词库

修改 `scripts/words_data.json`，然后:

```bash
python3 scripts/gen_words.py
```

会重新生成 `003/004/005-words-*.sql`。**注意**: 如果数据库里已有这些 changeset 跑过了，需要新增 `006-xxx.sql` 而不是修改旧文件，否则 Liquibase checksum 会冲突。

## 🧱 数据模型

| 表 | 说明 |
|---|---|
| `users` | 用户 (含 `daily_goal`、`role`) |
| `textbook` | 课本 (12 册，year+term+publisher) |
| `word` | 单词 (spelling/meaning/phonetic/unit_no) |
| `user_textbook` | 用户选了哪些课本 |
| `user_word_progress` | 单词学习进度 (stage/next_review_at/correct_count/wrong_count/mastered) |
| `review_log` | 每次答题流水 |

## 🔑 默认账号

- `admin / admin123` (ROLE_ADMIN)

## 📋 接下来可以做

- [x] ~~Docker 化 + GHCR 发布 (沿用 creditapp 套路)~~ ✅ 完成 v1.0.0
- [ ] 题目数据接入百度/有道发音 mp3 (替换 Web Speech)
- [ ] 家长端: 看小孩进度、设置每周复习目标
- [ ] 学习曲线图 (Chart.js)
- [ ] 导出错题本 PDF
