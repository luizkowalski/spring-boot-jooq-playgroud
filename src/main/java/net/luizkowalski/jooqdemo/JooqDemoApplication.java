package net.luizkowalski.jooqdemo;

import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import net.luizkowalski.jooqdemo.models.Author;
import org.jooq.DSLContext;
import org.jooq.example.flyway.db.postgres.tables.Authors;
import org.jooq.example.flyway.db.postgres.tables.Books;
import org.modelmapper.ModelMapper;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JooqDemoApplication implements CommandLineRunner {

  @Autowired
  DSLContext context;

  @Autowired
  ModelMapper mapper;

  @Autowired
  JdbcMapper<Author> jdbcMapper;

  public static void main(String[] args) {
    SpringApplication.run(JooqDemoApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    ResultSet resultSet = context.select(Authors.AUTHORS.ID,
        Authors.AUTHORS.NAME,
        Books.BOOKS.ID.as("books_id"),
        Books.BOOKS.ISBN.as("books_isbn"))
        .from(Authors.AUTHORS)
        .leftJoin(Books.BOOKS).on(Books.BOOKS.AUTHOR_ID.eq(Authors.AUTHORS.ID.cast(Integer.class)))
//        .where(Books.BOOKS.ISBN.eq("123125"))
        .orderBy(Authors.AUTHORS.ID.desc())
        .fetchResultSet();

//    Author author = jdbcMapper.map(resultSet);
    Stream<Author> authorsStream = jdbcMapper.stream(resultSet);
    authorsStream.forEach(System.out::println);

//    log.info("Author found: {} "+author.toString());
//    log.info("Found {} authors", authors.size());
//    authors.forEach(a -> log.info(a.toString() + " with " + a.getBooks().size() + " books!"));
  }
}
