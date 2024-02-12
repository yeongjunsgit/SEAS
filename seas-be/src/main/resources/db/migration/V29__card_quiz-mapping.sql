ALTER TABLE seas.card_quiz
    MODIFY flashcard_id int null;

ALTER TABLE seas.card_quiz
    MODIFY quiz_id int null;

-- 현재 시각 추가
UPDATE seas.card_quiz SET flashcard_id = 1, quiz_id = 1, created_at = NOW(), updated_at = NOW() WHERE id = 1;
UPDATE seas.card_quiz SET flashcard_id = 2, quiz_id = 2, created_at = NOW(), updated_at = NOW() WHERE id = 2;
UPDATE seas.card_quiz SET flashcard_id = 3, quiz_id = 3, created_at = NOW(), updated_at = NOW() WHERE id = 3;
UPDATE seas.card_quiz SET flashcard_id = 4, quiz_id = 4, created_at = NOW(), updated_at = NOW() WHERE id = 4;
UPDATE seas.card_quiz SET flashcard_id = 5, quiz_id = 5, created_at = NOW(), updated_at = NOW() WHERE id = 5;
UPDATE seas.card_quiz SET flashcard_id = 6, quiz_id = 6, created_at = NOW(), updated_at = NOW() WHERE id = 6;
UPDATE seas.card_quiz SET flashcard_id = 7, quiz_id = 7, created_at = NOW(), updated_at = NOW() WHERE id = 7;
UPDATE seas.card_quiz SET flashcard_id = 8, quiz_id = 8, created_at = NOW(), updated_at = NOW() WHERE id = 8;
UPDATE seas.card_quiz SET flashcard_id = 9, quiz_id = 9, created_at = NOW(), updated_at = NOW() WHERE id = 9;
UPDATE seas.card_quiz SET flashcard_id = 10, quiz_id = 10, created_at = NOW(), updated_at = NOW() WHERE id = 10;

-- mapping data 추가
INSERT INTO seas.card_quiz (id, flashcard_id, quiz_id, created_at, updated_at)
VALUES (11, 31, 11, NOW(), NOW()),
       (12, 32, 12, NOW(), NOW()),
       (13, 33, 13, NOW(), NOW()),
       (14, 34, 14, NOW(), NOW()),
       (15, 35, 15, NOW(), NOW()),
       (16, 36, 16, NOW(), NOW()),
       (17, 37, 17, NOW(), NOW()),
       (18, 38, 18, NOW(), NOW()),
       (19, 39, 19, NOW(), NOW()),
       (20, 40, 20, NOW(), NOW()),
       (21, NULL, 21, NOW(), NOW()),
       (22, NULL, 22, NOW(), NOW()),
       (23, NULL, 23, NOW(), NOW()),
       (24, NULL, 24, NOW(), NOW()),
       (25, NULL, 25, NOW(), NOW()),
       (26, NULL, 26, NOW(), NOW()),
       (27, NULL, 27, NOW(), NOW()),
       (28, NULL, 28, NOW(), NOW()),
       (29, NULL, 29, NOW(), NOW()),
       (30, NULL, 30, NOW(), NOW()),
       (31, 13, 31, NOW(), NOW()),
       (32, 17, 32, NOW(), NOW()),
       (33, NULL, 33, NOW(), NOW()),
       (34, 18, 34, NOW(), NOW()),
       (35, 16, 35, NOW(), NOW()),
       (36, 14, 36, NOW(), NOW()),
       (37, 20, 37, NOW(), NOW()),
       (38, 19, 38, NOW(), NOW()),
       (39, 12, 39, NOW(), NOW()),
       (40, 11, NULL, NOW(), NOW()),
       (41, 15, NULL, NOW(), NOW()),
       (42, 21, 40, NOW(), NOW()),
       (43, 22, 41, NOW(), NOW()),
       (44, 23, 42, NOW(), NOW()),
       (45, NULL, 43, NOW(), NOW()),
       (46, NULL, 44, NOW(), NOW()),
       (47, 26, 45, NOW(), NOW()),
       (48, NULL, 46, NOW(), NOW()),
       (49, 28, 47, NOW(), NOW()),
       (50, 29, 48, NOW(), NOW()),
       (51, 30, 49, NOW(), NOW()),
       (52, 24, NULL, NOW(), NOW()),
       (53, 25, NULL, NOW(), NOW()),
       (54, 27, NULL, NOW(), NOW()),
       (55, 51, 50, NOW(), NOW()),
       (56, 52, 51, NOW(), NOW()),
       (57, 54, 52, NOW(), NOW()),
       (58, 53, 53, NOW(), NOW()),
       (59, 55, 54, NOW(), NOW()),
       (60, 56, 55, NOW(), NOW()),
       (61, 57, 56, NOW(), NOW()),
       (62, 58, 57, NOW(), NOW()),
       (63, 59, 58, NOW(), NOW()),
       (64, 60, 59, NOW(), NOW()),
       (65, 43, 60, NOW(), NOW()),
       (66, 41, 61, NOW(), NOW()),
       (67, NULL, 62, NOW(), NOW()),
       (68, NULL, 63, NOW(), NOW()),
       (69, NULL, 64, NOW(), NOW()),
       (70, NULL, 65, NOW(), NOW()),
       (71, 47, 66, NOW(), NOW()),
       (72, 44, 67, NOW(), NOW()),
       (73, 50, 68, NOW(), NOW()),
       (74, 45, 69, NOW(), NOW()),
       (75, 42, NULL, NOW(), NOW()),
       (76, 46, NULL, NOW(), NOW()),
       (77, 48, NULL, NOW(), NOW()),
       (78, 49, NULL, NOW(), NOW());