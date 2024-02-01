create table quiz_answer
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    answer     varchar(255) null,
    quiz_id    int          null,
    constraint FKoxi2td1x8cc3y4a0vlsg2hfnc
        foreign key (quiz_id) references quiz (id)
);
