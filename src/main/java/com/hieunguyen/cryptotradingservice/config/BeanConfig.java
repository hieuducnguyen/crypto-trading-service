package com.hieunguyen.cryptotradingservice.config;

import com.hieunguyen.cryptotradingservice.properties.AppCommonProperties;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final AppCommonProperties appCommonProperties;

    @Bean
    public RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(appCommonProperties.getRestTemplateConnectionTimeout()); // Set the connection timeout
        factory.setReadTimeout(appCommonProperties.getRestTemplateReadTimeout()); // Set the read timeout
        return new RestTemplate(factory);
    }

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .usingDbTime()
                        .withTableName("batch_job")
                        .build()
        );
    }
}
