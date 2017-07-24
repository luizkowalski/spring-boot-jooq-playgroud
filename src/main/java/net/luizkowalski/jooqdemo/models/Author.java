package net.luizkowalski.jooqdemo.models;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.simpleflatmapper.map.annotation.Key;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {

  @Key
  private Long id;
  private String name;
  private List<Book> books = new ArrayList<>();
}
