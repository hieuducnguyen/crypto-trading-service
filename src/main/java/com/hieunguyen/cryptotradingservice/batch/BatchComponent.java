package com.hieunguyen.cryptotradingservice.batch;

import com.hieunguyen.cryptotradingservice.service.tasklet.PriceAggregateScheduleTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class BatchComponent {
    private final JobRepository jobRepository;
    private final PriceAggregateScheduleTasklet priceAggregateScheduleTasklet;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job aggregatePricesJob() {
        return new JobBuilder("aggregatePricesJob", jobRepository)
                .start(aggregatePricesStep())
                .listener(listener())
                .build();
    }

    @Bean
    public Step aggregatePricesStep() {
        return new StepBuilder("aggregatePricesStep", jobRepository)
                .tasklet(priceAggregateScheduleTasklet, transactionManager)
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(@NotNull JobExecution jobExecution) {
                log.info("Job started with status: " + jobExecution.getStatus());
            }

            @Override
            public void afterJob(@NotNull JobExecution jobExecution) {
                log.info("Job ended with status: " + jobExecution.getStatus());
            }
        };
    }


}
