

-- Update seas.favorite table
ALTER TABLE seas.favorite
DROP FOREIGN KEY FK5w3q9ljpthkixo71hetx3ired;

ALTER TABLE seas.favorite
    ADD CONSTRAINT FK5w3q9ljpthkixo71hetx3ired
        FOREIGN KEY (member_id) REFERENCES seas.member (id) ON DELETE CASCADE;

ALTER TABLE seas.favorite
DROP FOREIGN KEY FKmkk70saas31yfxmcmjw9tclyb;

ALTER TABLE seas.favorite
    ADD CONSTRAINT FKmkk70saas31yfxmcmjw9tclyb
        FOREIGN KEY (flashcard_id) REFERENCES seas.flashcard (id) ON DELETE CASCADE;

-- Update seas.member_badge table
ALTER TABLE seas.member_badge
DROP FOREIGN KEY member_badge_member_fk;

ALTER TABLE seas.member_badge
    ADD CONSTRAINT member_badge_member_fk
        FOREIGN KEY (member_id) REFERENCES seas.member (id) ON DELETE CASCADE;

ALTER TABLE seas.member_badge
DROP FOREIGN KEY member_badge_badge_fk;

ALTER TABLE seas.member_badge
    ADD CONSTRAINT member_badge_badge_fk
        FOREIGN KEY (badge_id) REFERENCES seas.badge (id) ON DELETE CASCADE;

-- Update seas.score_history table
ALTER TABLE seas.score_history
DROP FOREIGN KEY FK1a8hu7ckhsl4qo2jlabu0myqp;

ALTER TABLE seas.score_history
    ADD CONSTRAINT FK1a8hu7ckhsl4qo2jlabu0myqp
        FOREIGN KEY (member_id) REFERENCES seas.member (id) ON DELETE CASCADE;

ALTER TABLE seas.score_history
DROP FOREIGN KEY FKr0dx1ualdko25ur8j1c9k6ika;

ALTER TABLE seas.score_history
    ADD CONSTRAINT FKr0dx1ualdko25ur8j1c9k6ika
        FOREIGN KEY (category_id) REFERENCES seas.category (id) ON DELETE SET NULL;

-- Update seas.solved_quiz table
ALTER TABLE seas.solved_quiz
DROP FOREIGN KEY FKchrd6u487q2kp9bdtpovwkfuy;

ALTER TABLE seas.solved_quiz
    ADD CONSTRAINT FKchrd6u487q2kp9bdtpovwkfuy
        FOREIGN KEY (member_id) REFERENCES seas.member (id) ON DELETE CASCADE;

ALTER TABLE seas.solved_quiz
DROP FOREIGN KEY FKd2dl1knx9bewqq906vfiye41y;

ALTER TABLE seas.solved_quiz
    ADD CONSTRAINT FKd2dl1knx9bewqq906vfiye41y
        FOREIGN KEY (quiz_id) REFERENCES seas.quiz (id) ON DELETE CASCADE;

-- Update seas.streak table
ALTER TABLE seas.streak
DROP FOREIGN KEY FKhfmv2vccmigv977wx3kch1wg1;

ALTER TABLE seas.streak
    ADD CONSTRAINT FKhfmv2vccmigv977wx3kch1wg1
        FOREIGN KEY (member_id) REFERENCES seas.member (id) ON DELETE CASCADE;

-- Drop the existing foreign key constraint
ALTER TABLE seas.incorrect_note
DROP FOREIGN KEY FK56qhsn0o6o9bg0fnbo3ksxyol;

-- Add the foreign key constraint with ON DELETE CASCADE
ALTER TABLE seas.incorrect_note
    ADD CONSTRAINT FK56qhsn0o6o9bg0fnbo3ksxyol
        FOREIGN KEY (member_id) REFERENCES seas.member (id) ON DELETE CASCADE;

-- Drop the existing foreign key constraint
ALTER TABLE seas.factor
DROP FOREIGN KEY FKMemberIdByFactor;

-- Add the foreign key constraint with ON DELETE CASCADE
ALTER TABLE seas.factor
    ADD CONSTRAINT FKMemberIdByFactor
        FOREIGN KEY (member_id) REFERENCES seas.member (id) ON DELETE CASCADE;