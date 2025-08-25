package com.db.migrator.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class ReaderConfig {

    @Autowired
    @Qualifier("sourceDataSource")
    private DataSource sourceData;

    @Autowired
    private CustomRowMapper rowMapper;

    @Bean
    @StepScope
    public JdbcCursorItemReader<Map<String,Object>> reader(){
        return new JdbcCursorItemReaderBuilder<Map<String,Object>>()
                .name("Reader")
                .sql("select * from table1")
                .dataSource(sourceData)
                .rowMapper(new ColumnMapRowMapper())
                .build();
    }
}
