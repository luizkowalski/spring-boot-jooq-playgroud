package net.luizkowalski.jooqdemo.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.simpleflatmapper.jooq.SfmRecordMapperProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.ds")
@Slf4j
public class DataSourceConfiguration extends HikariConfig {

  @Bean
  public DataSource dataSource() throws SQLException {
    int cores = Runtime.getRuntime().availableProcessors();
    int poolSize = (cores * 2) + 1;
    log.info("Configuring Hikari pool size: " + poolSize);
    HikariDataSource ds = new HikariDataSource(this);
    ds.setMaximumPoolSize(poolSize);

    return ds;
  }

  @Bean
  public DSLContext getContext() throws SQLException {
    DSLContext context = DSL
        .using(dataSource(), SQLDialect.POSTGRES, new Settings().withRenderFormatted(true));
    context.configuration().set(new SfmRecordMapperProvider());
    return context;
  }
}