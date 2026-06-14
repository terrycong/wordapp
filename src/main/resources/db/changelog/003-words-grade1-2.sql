--liquibase formatted sql

--changeset wordapp:words-pepg1t1
INSERT INTO word (textbook_id, spelling, meaning, phonetic, unit_no, sort_order)
SELECT id, 'hello', '你好', '/həˈləʊ/', 1, 1 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'hi', '你好', '/haɪ/', 1, 2 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'I', '我', '/aɪ/', 1, 3 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'am', '是', '/æm/', 1, 4 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'goodbye', '再见', '/ˌɡʊdˈbaɪ/', 1, 5 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'bye', '再见', '/baɪ/', 1, 6 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'cat', '猫', '/kæt/', 2, 7 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'dog', '狗', '/dɒɡ/', 2, 8 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'panda', '熊猫', '/ˈpændə/', 2, 9 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'monkey', '猴子', '/ˈmʌŋki/', 2, 10 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'pig', '猪', '/pɪɡ/', 2, 11 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'duck', '鸭子', '/dʌk/', 2, 12 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'red', '红色', '/red/', 3, 13 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'yellow', '黄色', '/ˈjeləʊ/', 3, 14 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'blue', '蓝色', '/bluː/', 3, 15 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'green', '绿色', '/ɡriːn/', 3, 16 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'one', '一', '/wʌn/', 4, 17 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'two', '二', '/tuː/', 4, 18 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'three', '三', '/θriː/', 4, 19 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'four', '四', '/fɔː/', 4, 20 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'five', '五', '/faɪv/', 4, 21 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'pen', '钢笔', '/pen/', 5, 22 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'pencil', '铅笔', '/ˈpensl/', 5, 23 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'book', '书', '/bʊk/', 5, 24 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'bag', '书包', '/bæɡ/', 5, 25 FROM textbook WHERE code='PEP-G1-T1' UNION ALL
SELECT id, 'ruler', '尺子', '/ˈruːlə/', 5, 26 FROM textbook WHERE code='PEP-G1-T1';

--changeset wordapp:words-pepg1t2
INSERT INTO word (textbook_id, spelling, meaning, phonetic, unit_no, sort_order)
SELECT id, 'apple', '苹果', '/ˈæpl/', 1, 1 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'banana', '香蕉', '/bəˈnɑːnə/', 1, 2 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'orange', '橙子', '/ˈɒrɪndʒ/', 1, 3 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'pear', '梨', '/peə/', 1, 4 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'six', '六', '/sɪks/', 2, 5 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'seven', '七', '/ˈsevn/', 2, 6 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'eight', '八', '/eɪt/', 2, 7 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'nine', '九', '/naɪn/', 2, 8 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'ten', '十', '/ten/', 2, 9 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'mum', '妈妈', '/mʌm/', 3, 10 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'dad', '爸爸', '/dæd/', 3, 11 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'brother', '哥哥/弟弟', '/ˈbrʌðə/', 3, 12 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'sister', '姐姐/妹妹', '/ˈsɪstə/', 3, 13 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'face', '脸', '/feɪs/', 4, 14 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'eye', '眼睛', '/aɪ/', 4, 15 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'ear', '耳朵', '/ɪə/', 4, 16 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'nose', '鼻子', '/nəʊz/', 4, 17 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'mouth', '嘴巴', '/maʊθ/', 4, 18 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'hand', '手', '/hænd/', 5, 19 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'foot', '脚', '/fʊt/', 5, 20 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'leg', '腿', '/leɡ/', 5, 21 FROM textbook WHERE code='PEP-G1-T2' UNION ALL
SELECT id, 'arm', '胳膊', '/ɑːm/', 5, 22 FROM textbook WHERE code='PEP-G1-T2';

--changeset wordapp:words-pepg2t1
INSERT INTO word (textbook_id, spelling, meaning, phonetic, unit_no, sort_order)
SELECT id, 'school', '学校', '/skuːl/', 1, 1 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'classroom', '教室', '/ˈklɑːsruːm/', 1, 2 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'desk', '课桌', '/desk/', 1, 3 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'chair', '椅子', '/tʃeə/', 1, 4 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'teacher', '老师', '/ˈtiːtʃə/', 1, 5 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'student', '学生', '/ˈstjuːdnt/', 1, 6 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'tiger', '老虎', '/ˈtaɪɡə/', 2, 7 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'lion', '狮子', '/ˈlaɪən/', 2, 8 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'elephant', '大象', '/ˈelɪfənt/', 2, 9 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'bear', '熊', '/beə/', 2, 10 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'rabbit', '兔子', '/ˈræbɪt/', 2, 11 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'big', '大的', '/bɪɡ/', 3, 12 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'small', '小的', '/smɔːl/', 3, 13 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'tall', '高的', '/tɔːl/', 3, 14 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'short', '矮的/短的', '/ʃɔːt/', 3, 15 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'long', '长的', '/lɒŋ/', 3, 16 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'fat', '胖的', '/fæt/', 3, 17 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'thin', '瘦的', '/θɪn/', 3, 18 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'happy', '快乐的', '/ˈhæpi/', 4, 19 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'sad', '难过的', '/sæd/', 4, 20 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'hungry', '饿的', '/ˈhʌŋɡri/', 4, 21 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'tired', '累的', '/ˈtaɪəd/', 4, 22 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'good', '好的', '/ɡʊd/', 5, 23 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'bad', '坏的', '/bæd/', 5, 24 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'nice', '好的', '/naɪs/', 5, 25 FROM textbook WHERE code='PEP-G2-T1' UNION ALL
SELECT id, 'fine', '很好', '/faɪn/', 5, 26 FROM textbook WHERE code='PEP-G2-T1';

--changeset wordapp:words-pepg2t2
INSERT INTO word (textbook_id, spelling, meaning, phonetic, unit_no, sort_order)
SELECT id, 'sun', '太阳', '/sʌn/', 1, 1 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'moon', '月亮', '/muːn/', 1, 2 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'star', '星星', '/stɑː/', 1, 3 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'sky', '天空', '/skaɪ/', 1, 4 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'cloud', '云', '/klaʊd/', 1, 5 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'spring', '春天', '/sprɪŋ/', 2, 6 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'summer', '夏天', '/ˈsʌmə/', 2, 7 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'autumn', '秋天', '/ˈɔːtəm/', 2, 8 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'winter', '冬天', '/ˈwɪntə/', 2, 9 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'hot', '热的', '/hɒt/', 2, 10 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'cold', '冷的', '/kəʊld/', 2, 11 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'warm', '温暖的', '/wɔːm/', 2, 12 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'cool', '凉爽的', '/kuːl/', 2, 13 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'rain', '下雨', '/reɪn/', 3, 14 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'snow', '雪', '/snəʊ/', 3, 15 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'wind', '风', '/wɪnd/', 3, 16 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'water', '水', '/ˈwɔːtə/', 3, 17 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'rice', '米饭', '/raɪs/', 4, 18 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'noodle', '面条', '/ˈnuːdl/', 4, 19 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'bread', '面包', '/bred/', 4, 20 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'milk', '牛奶', '/mɪlk/', 4, 21 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'egg', '鸡蛋', '/eɡ/', 4, 22 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'fish', '鱼', '/fɪʃ/', 4, 23 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'meat', '肉', '/miːt/', 4, 24 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'bus', '公共汽车', '/bʌs/', 5, 25 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'bike', '自行车', '/baɪk/', 5, 26 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'plane', '飞机', '/pleɪn/', 5, 27 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'ship', '轮船', '/ʃɪp/', 5, 28 FROM textbook WHERE code='PEP-G2-T2' UNION ALL
SELECT id, 'train', '火车', '/treɪn/', 5, 29 FROM textbook WHERE code='PEP-G2-T2';
