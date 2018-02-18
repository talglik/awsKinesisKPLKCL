package com.nice.kinesisConsumer;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.common.Agent;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class KinesisConsumerUtils {
    private final String StreamName = "SpaceDS";

    AmazonKinesis kinesisClient = getAmazonKinesisClient();
    String sequenceNumber=null;

    private AmazonKinesis getAmazonKinesisClient() {
        return AmazonKinesisClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    @SneakyThrows
    public Agent getNextAgent() {
        ObjectMapper om = new ObjectMapper();
        List<Record> records;
        String shardIterator = getSharedIterator();
        int searchIteration=0;
        Agent agent = null;

        while (searchIteration < 50 && agent == null) {

            // Create a new getRecordsRequest with an existing shardIterator
            // Set the maximum records to return to 25
            GetRecordsRequest getRecordsRequest = new GetRecordsRequest();
            getRecordsRequest.setShardIterator(shardIterator);
            getRecordsRequest.setLimit(1);

            GetRecordsResult result = kinesisClient.getRecords(getRecordsRequest);

            // Put the result into record list. The result can be empty.
            records = result.getRecords();
            if (records.size() > 0) {

                agent = om.readValue(new String(records.get(0).getData().array(), "ASCII"), Agent.class);
                sequenceNumber = records.get(0).getSequenceNumber();
            }
            else Thread.sleep(100);

            shardIterator = result.getNextShardIterator();
            searchIteration++;
        }

        return agent;
    }

    // AT_SEQUENCE_NUMBER
    // AFTER_SEQUENCE_NUMBER
    // AT_TIMESTAMP
    // TRIM_HORIZON
    // LATEST
    private String getSharedIterator() {
        String shardIterator;
        GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest();
        getShardIteratorRequest.setStreamName(StreamName);
        getShardIteratorRequest.setShardId(getSharedId());
        if (sequenceNumber == null)
            getShardIteratorRequest.setShardIteratorType("TRIM_HORIZON");
        else {
            getShardIteratorRequest.setShardIteratorType("AFTER_SEQUENCE_NUMBER");
            getShardIteratorRequest.setStartingSequenceNumber(sequenceNumber);
        }

        GetShardIteratorResult getShardIteratorResult = kinesisClient.getShardIterator(getShardIteratorRequest);
        shardIterator = getShardIteratorResult.getShardIterator();
        return shardIterator;
    }

    private String getSharedId() {
        DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
        describeStreamRequest.setStreamName( StreamName );
        List<Shard> shards = new ArrayList<>();
        String exclusiveStartShardId = null;
        describeStreamRequest.setExclusiveStartShardId( exclusiveStartShardId );
        DescribeStreamResult describeStreamResult = kinesisClient.describeStream( describeStreamRequest );
        shards.addAll( describeStreamResult.getStreamDescription().getShards() );
        exclusiveStartShardId = shards.get(0).getShardId();
        return exclusiveStartShardId;
    }
}
