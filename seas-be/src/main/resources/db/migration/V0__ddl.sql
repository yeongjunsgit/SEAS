create table seas.member
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    email      varchar(255) not null,
    member_id  varchar(255) not null,
    nickname   varchar(255) not null,
    pwd        varchar(255) not null,
    point      int          null,
    constraint UK_4rw879c4q7wrgi3v64cy73b17
        unique (member_id),
    constraint UK_mbmcqelty0fbrvxp1q58dn57t
        unique (email)
);



create table seas.category
(
    id               int auto_increment
        primary key,
    created_at       datetime(6)  null,
    updated_at       datetime(6)  null,
    background_color varchar(255) null,
    line_color       varchar(255) null,
    name             varchar(255) null
);

create table seas.flashcard
(
    id          int auto_increment
        primary key,
    created_at  datetime(6)  null,
    updated_at  datetime(6)  null,
    content     varchar(255) null,
    keyword     varchar(255) null,
    category_id int          null,
    constraint FKey4x0mi4bmbnhu01om7b06dl2
        foreign key (category_id) references seas.category (id)
);

create table seas.quiz
(
    id          int auto_increment
        primary key,
    created_at  datetime(6)  null,
    updated_at  datetime(6)  null,
    hint        varchar(255) null,
    problem     varchar(255) null,
    category_id int          null,
    constraint FK82x9fxd5tsbb3i1ewrp3cr8xa
        foreign key (category_id) references seas.category (id)
);

create table seas.ranker
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    member_id  varchar(255) null,
    nickname   varchar(255) null,
    point      int          null,
    tier       varchar(255) null
);

create table seas.solved_quiz
(
    id            int auto_increment
        primary key,
    created_at    datetime(6) null,
    updated_at    datetime(6) null,
    correct_count int         not null,
    failed_count  int         not null,
    member_id     int         null,
    quiz_id       int         null,
    constraint FKchrd6u487q2kp9bdtpovwkfuy
        foreign key (member_id) references seas.member (id),
    constraint FKd2dl1knx9bewqq906vfiye41y
        foreign key (quiz_id) references seas.quiz (id)
);

create table seas.tier
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    max_score  int          not null,
    min_score  int          not null,
    name       varchar(255) not null,
    constraint UK_41thf79xhgrvjrsjgafbifysh
        unique (name),
    constraint UK_ayps9i2gsfu208qjja42nvgql
        unique (max_score),
    constraint UK_lg2lg4ufuwskb9qtwn71ctat0
        unique (min_score)
);

