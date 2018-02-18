package com.nice.kinesisConsumer;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.nice.kinesisConsumer.KinesisUtilsKCL;

/**
 * Used to create new record processors.
 */
public class KinesisFactory implements IRecordProcessorFactory {
    /**
     * {@inheritDoc}
     */
    @Override
    public IRecordProcessor createProcessor() {
        return new KinesisUtilsKCL();
    }
}
