package com.nice.kinesisProducer;

import com.nice.common.Agent;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("KinesisProducer")
public class KinesisController {

    @Autowired
    KinesisUtils kinesisUtil;

    @SneakyThrows
    @RequestMapping(path = "agents", method = RequestMethod.GET)
    public Agent[] getAllAgents() {
        return null;
    }

    @RequestMapping(path = "put", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addMessage(@Valid @RequestBody Agent agent) {
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(kinesisUtil.addAgent(agent),HttpStatus.OK);
        return responseEntity;
    }
}

