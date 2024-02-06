create table seas.incorrect_note
(
    id         int auto_increment
        primary key,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    member_id  int         null,
    quiz_id    int         null,
    constraint FK56qhsn0o6o9bg0fnbo3ksxyol
        foreign key (member_id) references seas.member (id),
    constraint FKgyjffmewlxblhp4py8t71ypyk
        foreign key (quiz_id) references seas.member (id)
);

