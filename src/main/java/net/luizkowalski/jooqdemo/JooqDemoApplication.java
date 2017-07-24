package net.luizkowalski.jooqdemo;

import static org.jooq.example.flyway.db.postgres.tables.Authors.AUTHORS;
import static org.jooq.example.flyway.db.postgres.tables.Books.BOOKS;

import java.sql.ResultSet;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import net.luizkowalski.jooqdemo.models.Author;
import org.jooq.DSLContext;
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
    ResultSet resultSet = context.select(
        AUTHORS.ID,
        AUTHORS.NAME,
        BOOKS.ID.as("books_id"),
        BOOKS.ISBN.as("books_isbn"))
        .from(AUTHORS)
        .leftJoin(BOOKS).on(BOOKS.AUTHOR_ID.eq(AUTHORS.ID.cast(Integer.class)))
        .where(BOOKS.ISBN.eq("123125"))
        .orderBy(AUTHORS.ID.desc())
        .fetchResultSet();

    Stream<Author> authorsStream = jdbcMapper.stream(resultSet);
    authorsStream.forEach(System.out::println);
  }
}
