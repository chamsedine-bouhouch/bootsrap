package com.bouhouch.bootsrap.config;

import com.bouhouch.bootsrap.processor.OrderProcessor;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.ChunkOrientedStepBuilder;
import org.springframework.batch.core.step.item.ChunkOrientedStep;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.bouhouch.bootsrap.model.OrderInput;

@Configuration

public class ImportOrdersJobConfig {

    private static final int CHUNK_SIZE = 10;

    @Bean
    Job importOrdersJob(JobRepository jobRepository, Step importOrdersStep) {
        return new JobBuilder("importOrdersJob", jobRepository)
                .start(importOrdersStep)
                .build();
    }

    @Bean
    Step importOrdersStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<OrderInput> orderReader,
            OrderProcessor orderProcessor,
            ItemWriter<OrderInput> orderWriter) {
        return new ChunkOrientedStepBuilder<OrderInput, OrderInput>(
                "importOrdersStep", jobRepository, CHUNK_SIZE)
                .reader(orderReader)
                .processor(orderProcessor)
                .writer(orderWriter)
                .transactionManager(transactionManager)
                .faultTolerant()
                .skip(IllegalArgumentException.class)
                .skipLimit(10)
                .build();
    }
}
