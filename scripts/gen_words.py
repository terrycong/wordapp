#!/usr/bin/env python3
"""生成 wordapp 的人教版 PEP 1-6 年级词表 Liquibase SQL 文件"""
import os, json

OUTDIR = "/vol1/@apphome/trim.openclaw/data/workspace/wordapp/src/main/resources/db/changelog"
DATA_FILE = os.path.join(os.path.dirname(__file__), "words_data.json")

with open(DATA_FILE, "r", encoding="utf-8") as f:
    BOOKS = json.load(f)

def esc(s):
    return s.replace("'", "''")

def book_to_sql(code, words):
    lines = ["INSERT INTO word (textbook_id, spelling, meaning, phonetic, unit_no, sort_order)"]
    parts = []
    for i, w in enumerate(words, start=1):
        spelling, meaning, phonetic, unit = w
        parts.append(f"SELECT id, '{esc(spelling)}', '{esc(meaning)}', '{esc(phonetic)}', {unit}, {i} FROM textbook WHERE code='{code}'")
    lines.append(" UNION ALL\n".join(parts) + ";")
    return "\n".join(lines)

groups = {
    "003-words-grade1-2.sql": ["PEP-G1-T1","PEP-G1-T2","PEP-G2-T1","PEP-G2-T2"],
    "004-words-grade3-4.sql": ["PEP-G3-T1","PEP-G3-T2","PEP-G4-T1","PEP-G4-T2"],
    "005-words-grade5-6.sql": ["PEP-G5-T1","PEP-G5-T2","PEP-G6-T1","PEP-G6-T2"],
}

for fname, codes in groups.items():
    out = ["--liquibase formatted sql", ""]
    for code in codes:
        if code not in BOOKS:
            continue
        cs_id = code.lower().replace("-", "")
        out.append(f"--changeset wordapp:words-{cs_id}")
        out.append(book_to_sql(code, BOOKS[code]))
        out.append("")
    path = os.path.join(OUTDIR, fname)
    with open(path, "w", encoding="utf-8") as f:
        f.write("\n".join(out))
    print(f"wrote {path} with {sum(len(BOOKS.get(c, [])) for c in codes)} words")
print("done")
