create table seas.streak
(
    id         int auto_increment
        primary key,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    quiz_count int         not null,
    member_id  int         null,
    constraint FKhfmv2vccmigv977wx3kch1wg1
        foreign key (member_id) references seas.member (id)
);

