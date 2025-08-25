package com.db.migrator.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.support.ColumnMapItemPreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Configuration
public class WriterConfig {
    @Autowired
    @Qualifier("targetDataSource")
    private DataSource targetDataSource;

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Map<String, Object>>writer(){
        return new JdbcBatchItemWriterBuilder<Map<String,Object>>()
                .dataSource(targetDataSource)
                .sql("INSERT INTO table1 VALUES(:name)")
                .itemSqlParameterSourceProvider(MapSqlParameterSource::new)
                .build();

    }
}
