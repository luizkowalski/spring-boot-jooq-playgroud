create table books(
  id bigserial not null primary key,
  isbn varchar not null,
  "name" varchar not null,
  author_id integer not null,
  CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES authors(id)
);

create index index_author_on_books on books(author_id);
create index index_isbn_on_books on books(isbn);