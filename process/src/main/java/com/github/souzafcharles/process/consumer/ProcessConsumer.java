package com.github.souzafcharles.process.consumer;

import com.github.souzafcharles.process.dto.ProcessRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProcessConsumer {

    @RabbitListener(queues = "${broker.queue.process.name}")
    public void listenerProcessQueue(ProcessRequestDTO dto) {
        System.out.println(dto.title());
    }
}