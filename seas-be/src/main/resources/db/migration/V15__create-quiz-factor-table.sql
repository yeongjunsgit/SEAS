
create table seas.card_quiz
(
    id              int auto_increment primary key,
    flashcard_id    int         not null,
    quiz_id         int         not null,

    constraint FKFlashCardIdByCardQuiz
        foreign key (flashcard_id) references seas.flashcard (id),
    constraint FKQuizIdByCardQuiz
        foreign key (quiz_id) references seas.quiz (id)
);


create table seas.factor
(
    id              int auto_increment primary key,
    quiz_interval   double       null,
    ef              double       null,
    card_quiz_id    int          null,
    member_id       int          null,
    created_at      datetime(6)  null,
    updated_at      datetime(6)  null,

    constraint FKMemberIdByFactor
        foreign key (member_id) references seas.member (id),
    constraint FKCardQuizIdByFactor
        foreign key (card_quiz_id) references seas.card_quiz (id)
);

