package net.luizkowalski.jooqdemo;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.luizkowalski.jooqdemo.models.Author;
import org.jooq.DSLContext;
import org.jooq.example.flyway.db.postgres.tables.Authors;
import org.jooq.example.flyway.db.postgres.tables.Books;
import org.modelmapper.ModelMapper;
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

  public static void main(String[] args) {
    SpringApplication.run(JooqDemoApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    List<Author> authors = context.select(Authors.AUTHORS.ID, Authors.AUTHORS.NAME)
        .from(Authors.AUTHORS)
        .leftJoin(Books.BOOKS).on(Books.BOOKS.AUTHOR_ID.eq(Authors.AUTHORS.ID.cast(Integer.class)))
        .where(Books.BOOKS.ISBN.eq("123125"))
        .orderBy(Authors.AUTHORS.ID.desc())
        .fetch().map(r -> mapper.map(r, Author.class));

    log.info("Found {} authors", authors.size());
    authors.forEach(a -> log.info(a.toString() + " with " + a.getBooks().size() + " books!"));
  }
}
