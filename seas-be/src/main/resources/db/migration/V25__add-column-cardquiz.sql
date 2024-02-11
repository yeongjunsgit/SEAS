ALTER TABLE card_quiz
ADD COLUMN created_at datetime(6) DEFAULT NULL,
ADD COLUMN updated_at datetime(6) DEFAULT NULL;