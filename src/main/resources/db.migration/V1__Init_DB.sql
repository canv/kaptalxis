create sequence hibernate_sequence start 1 increment 1;

create table book (
    id uuid not null,
    title varchar(100) not null,
    description text,
    author varchar(100) not null,
    isbn varchar(100),
    printYear int4,
    readAlready boolean,
    imgPath varchar(2048)
);
