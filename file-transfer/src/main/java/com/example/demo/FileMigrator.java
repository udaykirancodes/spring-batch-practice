package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class FileMigrator {

    @Bean
    @StepScope
    public FlatFileItemReader<String> reader(){
        return new FlatFileItemReaderBuilder<String>().name("reader-name")
                .resource(new FileSystemResource("src/main/resources/input.txt"))
                .lineMapper((line, lineNumber) -> line)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<String> writer(){
        return new FlatFileItemWriterBuilder<String>().name("writer-name")
                .resource(new FileSystemResource("src/main/resources/output.txt"))
                .lineAggregator(lineArgg -> lineArgg)
                .build();
    }


    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> item;
    }

    @Bean
    public Step migrateStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return (Step) new StepBuilder("step-name",jobRepository)
                .<String,String>chunk(5,platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public PlatformTransactionManager transactionManager;

    @Bean
    public Job migrateJob() {
        return new JobBuilder("migrateJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(migrateStep(jobRepository, transactionManager))
                .build();
    }
}

