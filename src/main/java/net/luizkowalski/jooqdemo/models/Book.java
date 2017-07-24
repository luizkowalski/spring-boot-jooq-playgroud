package net.luizkowalski.jooqdemo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleflatmapper.map.annotation.Key;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

  @Key
  private Long id;
  private String isbn;
  private Author Author;
}
