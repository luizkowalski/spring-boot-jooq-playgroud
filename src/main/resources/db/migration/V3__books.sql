create table books(
  id integer not null primary key,
  isbn varchar not null,
  name varchar not null,
  author_id integer not null,
  CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES authors(id)
);