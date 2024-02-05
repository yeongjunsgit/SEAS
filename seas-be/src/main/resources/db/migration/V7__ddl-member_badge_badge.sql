create table badge
(
    id          int             auto_increment
        primary key,
    created_at  datetime(6)     null,
    updated_at  datetime(6)     null,
    name        varchar(50)     not null
);

create table member_badge
(
    id          int             auto_increment
        primary key,
    created_at  datetime(6)     null,
    updated_at  datetime(6)     null,
    member_id   int             not null,
    badge_id    int             not null,
    constraint member_badge_badge_fk
        foreign key (badge_id) references seas.badge (id),
    constraint member_badge_member_fk
        foreign key (member_id) references seas.member (id)
);



