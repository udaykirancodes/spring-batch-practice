package com.db.migrator;

import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;
import java.util.UUID;

@Service
public class DataMigrator {



    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("tableMigrationJob") Job tableMigrationJob;

    public void migrateData() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {


        JobParameters jobParameters = new JobParametersBuilder()
                .addString("runId", UUID.randomUUID().toString()) // Ensures this job instance is unique and restartable
                .addString("tableName", "table1")
                .toJobParameters();

        // 4. Launch the job
        jobLauncher.run(tableMigrationJob, jobParameters);

    }
}
