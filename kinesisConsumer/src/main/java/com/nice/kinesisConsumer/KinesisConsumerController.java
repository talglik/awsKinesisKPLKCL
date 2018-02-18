package com.nice.kinesisConsumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.common.Agent;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("kinesisConsumer")
public class KinesisConsumerController {

    @Autowired
    KinesisConsumerUtils kinesisConsumerUtils;

    @SneakyThrows
    @RequestMapping(path = "next/agent", method = RequestMethod.GET)
    public Agent getNextAgent() {
        return kinesisConsumerUtils.getNextAgent();
    }
}
