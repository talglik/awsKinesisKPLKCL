package com.nice.kinesisConsumer;

//import com.nice.common.Java8DateTimeConfiguration;
import com.nice.common.Java8DateTimeConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Java8DateTimeConfiguration.class)
public class KinesisConsumerConfig {

    @Bean
    KinesisConsumerController getController() {
        return new KinesisConsumerController();
    }

    @Bean
    KinesisConsumerUtils getKinesisUtils() {
        return new KinesisConsumerUtils();
    }

}
