create table diplomas
(
    id          serial not null,
    title_en     text,
    title_pl     text,
    description text,
    project_id   int4,

    primary key (id),
    unique (project_id)
);

create table diplomas_chapters
(
    id           serial not null,
    diploma_id   int4,
    title        text,
    description  text,
    student_index text,

    primary key (id),
    unique (diploma_id, student_index)
);

alter table diplomas_chapters
    add constraint fk_diplomas_chapters_diploma_id
        foreign key (diploma_id)
            references diplomas;