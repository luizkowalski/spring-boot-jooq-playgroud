package net.luizkowalski.jooqdemo.config.mapper;

import net.luizkowalski.jooqdemo.models.Author;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jooq.RecordValueReader;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecordMapperConfigure {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().addValueReader(new RecordValueReader());
    modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);

    return modelMapper;
  }

  @Bean
  public JdbcMapper jdbcMapper() {
    return JdbcMapperFactory.newInstance().newMapper(Author.class);
  }
}
