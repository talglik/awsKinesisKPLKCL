
package com.nice.kinesisProducer;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.*;
import com.amazonaws.services.kinesis.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.common.Agent;
import lombok.SneakyThrows;
import org.springframework.util.SerializationUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class KinesisUtils {
    private final String StreamName = "SpaceDS";

    AmazonKinesis kinesisClient = getAmazonKinesisClient();
    String sequenceNumber;

    @SneakyThrows
    public KinesisUtils()
    {
        deleteStream();
        createStream();
    }

    private void deleteStream()
    {
        ListStreamsResult listStreamsResult = kinesisClient.listStreams();
        if (listStreamsResult.getStreamNames().contains(StreamName)) {
            kinesisClient.deleteStream(StreamName);
        }
        do {
            listStreamsResult = kinesisClient.listStreams();
        }
        while (listStreamsResult.getStreamNames().contains(StreamName));
    }

    @SneakyThrows
    private void createStream()
    {
        String streamStatus = "NotActive";
        CreateStreamRequest csr = new CreateStreamRequest();
        csr.setStreamName(StreamName);
        csr.setShardCount(1);

        kinesisClient.createStream(csr);

        DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
        describeStreamRequest.setStreamName( StreamName );

        do {
            Thread.sleep(200);

            DescribeStreamResult describeStreamResponse = kinesisClient.describeStream(describeStreamRequest);
            streamStatus = describeStreamResponse.getStreamDescription().getStreamStatus();
        }
        while (!streamStatus.equals( "ACTIVE" ));
    }

    @SneakyThrows
    public boolean addAgent(Agent agent) {
        ObjectMapper om = new ObjectMapper();

        PutRecordsRequest putRecordsRequest  = new PutRecordsRequest();
        putRecordsRequest.setStreamName(StreamName);
        List<PutRecordsRequestEntry> putRecordsRequestEntryList  = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            PutRecordsRequestEntry putRecordsRequestEntry  = new PutRecordsRequestEntry();
            putRecordsRequestEntry.setData(ByteBuffer.wrap(om.writeValueAsString(agent).getBytes()));// String.valueOf(i).getBytes()));
            putRecordsRequestEntry.setPartitionKey(String.format("partitionKey-%d", i));
            putRecordsRequestEntryList.add(putRecordsRequestEntry);
        }

        putRecordsRequest.setRecords(putRecordsRequestEntryList);
        PutRecordsResult putRecordsResult  = kinesisClient.putRecords(putRecordsRequest);// Push
        sequenceNumber = putRecordsResult.getRecords().get(0).getSequenceNumber();
        System.out.println("Put Result" + putRecordsResult);

        return true;
    }

    private AmazonKinesis getAmazonKinesisClient() {
        return AmazonKinesisClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build();
    }
}
