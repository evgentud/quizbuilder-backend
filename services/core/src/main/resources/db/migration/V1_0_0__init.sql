create table quiz_category
(
    uuid               uuid                  not null primary key,
    created_date       timestamp(6)          not null,
    is_deleted         boolean default false not null,
    last_modified_date timestamp(6),
    name               varchar(255),
    parent_uuid        uuid
        constraint fk_quiz_category_parent
            references quiz_category
);

create table quiz
(
    uuid               uuid                  not null primary key,
    created_date       timestamp(6)          not null,
    is_deleted         boolean default false not null,
    last_modified_date timestamp(6),
    owner_uuid         varchar(255)          not null,
    title              varchar(255),
    type               varchar(255)
        constraint chk_quiz_type
            check ((type)::text = ANY ((ARRAY ['FLASH_CARD'::character varying, 'QUIZ'::character varying])::text[])),
    category_uuid      uuid                  not null
        constraint fk_quiz_category
            references quiz_category(uuid)
);

create table quiz_answer
(
    uuid               uuid                  not null primary key,
    created_date       timestamp(6)          not null,
    is_deleted         boolean default false not null,
    last_modified_date timestamp(6),
    answer             varchar(255),
    quiz_question_uuid uuid                  not null,
    quiz_uuid          uuid                  not null
        constraint fk_quiz_answer_quiz
            references quiz(uuid)
);

create table quiz_question
(
    uuid                uuid                  not null primary key,
    created_date        timestamp(6)          not null,
    is_deleted          boolean default false not null,
    last_modified_date  timestamp(6),
    question            varchar(255),
    correct_answer_uuid uuid
        constraint uk_correct_answer_unique
            unique
        constraint fk_quiz_question_correct_answer
            references quiz_answer(uuid),
    quiz_uuid           uuid                  not null
        constraint fk_quiz_question_quiz
            references quiz(uuid)
);

alter table quiz_answer
    add constraint fk_quiz_answer_question
        foreign key (quiz_question_uuid) references quiz_question;

create table quiz_tag
(
    uuid               uuid                  not null primary key,
    name               varchar(255)          not null,
    created_date       timestamp(6)          not null,
    is_deleted         boolean default false not null,
    last_modified_date timestamp(6)
);

create table quiz_quiz_tag
(
    quiz_uuid uuid not null
        constraint fk_quiz_tag_quiz
            references quiz(uuid),
    tag_uuid  uuid not null
        constraint fk_quiz_tag_tag
            references quiz_tag(uuid)
);
