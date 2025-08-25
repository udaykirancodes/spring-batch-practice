package com.db.migrator.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
public class BatchConfig {
    @Bean
    public Step genericMigrationStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     JdbcCursorItemReader<Map<String, Object>> reader,
                                     JdbcBatchItemWriter<Map<String, Object>> writer) {
        return new StepBuilder("genericMigrationStep", jobRepository)
                .<Map<String, Object>, Map<String, Object>>chunk(100, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Autowired
    JobRepository jobRepository;

    @Bean
    public Job tableMigrationJob(JobRepository jobRepository, Step genericMigrationStep) {
        return new JobBuilder("tableMigrationJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(genericMigrationStep)
                .end()
                .build();
    }
}
