package com.hieunguyen.cryptotradingservice.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import java.time.LocalDateTime;

@Log4j2
@Configuration
@EnableScheduling
@RequiredArgsConstructor
@EnableSchedulerLock(defaultLockAtMostFor = "PT10S", defaultLockAtLeastFor = "PT10S", interceptMode = EnableSchedulerLock.InterceptMode.PROXY_METHOD)
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job aggregatePricesJob;
    private static final String AGGREGATE_PRICE_SCHEDULER = "AggregatePriceScheduler";

    @Schedules({@Scheduled(fixedRateString = "${app.batch.fix-rate-aggregate-price-scheduler-in-millisecond}",
            zone = "${app.batch.scheduling-job-zone:Asia/Ho_Chi_Minh}")})
    @SchedulerLock(name = "AggregatePriceScheduler")
    public void scheduleRetrievePrice() {
        log.info("Schedule Job {} started at {}, threadId={}", AGGREGATE_PRICE_SCHEDULER, LocalDateTime.now(),
                Thread.currentThread().getId());
        try {
            JobParameters param = new JobParametersBuilder()
                    .addString("JobId", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(aggregatePricesJob, param);
            log.info("Schedule Job {} run successfully at {}, threadId={}", AGGREGATE_PRICE_SCHEDULER, LocalDateTime.now(),
                    Thread.currentThread().getId());
        } catch (Exception ex) {
            log.error("Error executing scheduler {}, {}, {}", AGGREGATE_PRICE_SCHEDULER, ex.getMessage(), ex);
        }
        log.info("Scheduled Job {} completed at {}, threadId={}", AGGREGATE_PRICE_SCHEDULER, LocalDateTime.now(),
                Thread.currentThread().getId());
    }
}
