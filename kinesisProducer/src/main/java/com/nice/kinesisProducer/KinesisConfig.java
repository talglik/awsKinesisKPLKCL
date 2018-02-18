package com.nice.kinesisProducer;

import com.nice.common.Java8DateTimeConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@Import({Java8DateTimeConfiguration.class})
public class KinesisConfig {
    @Bean
    public RestOperations getRestOperations() {
        return new RestTemplate();
    }

      @Bean
    public KinesisController getKinesisController() {
        return new KinesisController();
    }

    @Bean(name = "KinesisUtils")
    @Lazy
    public KinesisUtils getKinesis() {
        return new KinesisUtils();
    }

}
