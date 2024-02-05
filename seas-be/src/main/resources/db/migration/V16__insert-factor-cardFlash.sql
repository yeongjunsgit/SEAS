
INSERT INTO seas.flashcard (id, keyword, created_at, updated_at, category_id)
VALUES
    (1, '기본키 (Primary Key)', NOW(), NOW(), 1),
    (2, '테이블 (Table)', NOW(), NOW(), 1),
    (3, '외래 키 (Foreign Key)', NOW(), NOW(), 1),
    (4, '인덱스 (Index)', NOW(), NOW(), 1),
    (5, '트랜잭션 (Transaction)', NOW(), NOW(), 1),
    (6, '스키마 (Schema)', NOW(), NOW(), 1),
    (7, '정규화 (Normalization)', NOW(), NOW(), 1),
    (8, '뷰 (View)', NOW(), NOW(), 1),
    (9, 'SQL; Structured Query Language', NOW(), NOW(), 1),
    (10, '트리거 (Trigger)', NOW(), NOW(), 1);


INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(1,1,1);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(2,2,2);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(3,3,3);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(4,4,4);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(5,5,5);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(6,6,6);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(7,7,7);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(8,8,8);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(9,9,9);
INSERT INTO seas.card_quiz(id, quiz_id, flashcard_id)
VALUES(10,10,10);




INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(1,1,1,1,1.3);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(2,1,2,1,1.3);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(3,1,3,1,1.3);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(4,1,4,2,1.4);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(5,1,5,4,1.7);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(6,1,6,1,1.3);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(7,1,7,1,1.3);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(8,1,8,1,1.3);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(9,1,9,1,1.3);
INSERT INTO seas.factor(id, member_id, card_quiz_id, quiz_interval, ef)
VALUES(10,1,10,1,1.3);
