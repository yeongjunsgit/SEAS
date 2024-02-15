create table seas.favorite
(
    id           int auto_increment
        primary key,
    created_at   datetime(6) null,
    updated_at   datetime(6) null,
    flashcard_id int         null,
    member_id    int         null,
    constraint FK5w3q9ljpthkixo71hetx3ired
        foreign key (member_id) references seas.member (id),
    constraint FKmkk70saas31yfxmcmjw9tclyb
        foreign key (flashcard_id) references seas.flashcard (id)
);

