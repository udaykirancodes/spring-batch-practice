package com.db.migrator;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class MigratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MigratorApplication.class, args);
	}

}
