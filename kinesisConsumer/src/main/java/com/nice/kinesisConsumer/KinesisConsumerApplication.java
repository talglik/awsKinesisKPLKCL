package com.nice.kinesisConsumer;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Configuration
@EnableAutoConfiguration
@Import(KinesisConsumerConfig.class)
public class KinesisConsumerApplication {

	public static void main(String[] args) {
//		SpringApplication.run(KinesisConsumerApplication.class, args);
		read();
	}

	public static void read() {
		String workerId = UUID.randomUUID().toString();

		KinesisClientLibConfiguration kinesisClientLibConfiguration =
				new KinesisClientLibConfiguration(
						"KinesisSample",
						"SpaceDS",
						new DefaultAWSCredentialsProviderChain(),
						workerId)
						.withRegionName(Regions.US_EAST_2.getName())
						.withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON);

		final IRecordProcessorFactory recordProcessorFactory = new KinesisFactory();
		final Worker worker = new Worker(recordProcessorFactory, kinesisClientLibConfiguration);
		worker.run();
	}
}


