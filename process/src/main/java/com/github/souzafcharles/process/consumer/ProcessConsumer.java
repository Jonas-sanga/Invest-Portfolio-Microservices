package com.github.souzafcharles.process.consumer;

import com.github.souzafcharles.process.dto.PortfolioRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProcessConsumer {

    @RabbitListener(queues = "${broker.queue.process.name}")
    public void listenerProcessQueue(PortfolioRequestDTO dto) {
        System.out.println(dto.title());
    }
}