delete from book;

insert into book (id, title, author, description, isbn, print_year, read_already, img_path, file_path)
    values (
    '411c8960-047f-4d15-bccf-1397d9f00d05',
    'testTitleOne',
    'testAuthor',
    'testDescriptionOne',
    'testISBN',
    '1111',
    'false',
    'test/img/path',
    'test/file/path'),

    ('421c8960-047f-4d15-bccf-1397d9f00d05',
    'testTitleTwo',
    'testAuthor',
    'testDescriptionTwo',
    'testISBN',
    '1111',
    'false',
    'test/img/path',
    'test/file/path');