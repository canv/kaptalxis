create sequence hibernate_sequence start 1 increment 1;

create table book (
    id uuid not null,
    title varchar(100) not null,
    description text,
    author varchar(100) not null,
    isbn varchar(100),
    print_year int4,
    read_already boolean,
    img_path varchar(2048),
    file_path varchar(2048) not null
);
