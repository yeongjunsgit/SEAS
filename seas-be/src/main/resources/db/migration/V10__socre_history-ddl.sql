create table score_history
(
    id          int auto_increment
        primary key,
    created_at  datetime(6) null,
    updated_at  datetime(6) null,
    score       int         not null,
    category_id int         null,
    member_id   int         null,
    constraint FK1a8hu7ckhsl4qo2jlabu0myqp
        foreign key (member_id) references member (id),
    constraint FKr0dx1ualdko25ur8j1c9k6ika
        foreign key (category_id) references category (id)
);