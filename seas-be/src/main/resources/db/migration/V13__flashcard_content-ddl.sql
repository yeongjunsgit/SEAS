ALTER TABLE seas.flashcard
DROP COLUMN content;

CREATE TABLE seas.flashcard_content (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    created_at   DATETIME(6) NULL,
    updated_at   DATETIME(6) NULL,
    content      VARCHAR(255) NULL,
    flashcard_id INT NULL,
    CONSTRAINT FKeyFlashcardContent
        FOREIGN KEY (flashcard_id) REFERENCES seas.flashcard (id)
);