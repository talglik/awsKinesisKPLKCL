package com.nice.kinesisProducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableRetry
@EnableAsync
@Slf4j
@Import({KinesisConfig.class})
public class KinesisApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(KinesisApplication.class, args);
        System.out.println("Hello");
    }
}
