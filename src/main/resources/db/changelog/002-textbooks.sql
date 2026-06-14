--liquibase formatted sql

--changeset wordapp:002-textbooks
INSERT INTO textbook (code, name, grade, term, publisher, sort_order) VALUES
('PEP-G1-T1', '人教版PEP 一年级上册', 1, 1, '人教版PEP一起点', 1),
('PEP-G1-T2', '人教版PEP 一年级下册', 1, 2, '人教版PEP一起点', 2),
('PEP-G2-T1', '人教版PEP 二年级上册', 2, 1, '人教版PEP一起点', 3),
('PEP-G2-T2', '人教版PEP 二年级下册', 2, 2, '人教版PEP一起点', 4),
('PEP-G3-T1', '人教版PEP 三年级上册', 3, 1, '人教版PEP三起点', 5),
('PEP-G3-T2', '人教版PEP 三年级下册', 3, 2, '人教版PEP三起点', 6),
('PEP-G4-T1', '人教版PEP 四年级上册', 4, 1, '人教版PEP三起点', 7),
('PEP-G4-T2', '人教版PEP 四年级下册', 4, 2, '人教版PEP三起点', 8),
('PEP-G5-T1', '人教版PEP 五年级上册', 5, 1, '人教版PEP三起点', 9),
('PEP-G5-T2', '人教版PEP 五年级下册', 5, 2, '人教版PEP三起点', 10),
('PEP-G6-T1', '人教版PEP 六年级上册', 6, 1, '人教版PEP三起点', 11),
('PEP-G6-T2', '人教版PEP 六年级下册', 6, 2, '人教版PEP三起点', 12);
