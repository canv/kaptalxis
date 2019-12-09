create sequence hibernate_sequence start 1 increment 1;

create table books (
    id uuid not null,
    title varchar(100) not null,
    author varchar(100) not null,
    isbn varchar(20),
    print_year int4 check (print_year>=1 AND print_year<=2222),
    description text,
    read_already boolean,
    img_path varchar(2048),
    file_path varchar(2048),
    primary key (id)
);