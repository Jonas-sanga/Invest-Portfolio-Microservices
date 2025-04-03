package com.github.souzafcharles.process.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProcessConsumer {

    @RabbitListener(queues = "${broker.queue.process.name}")
    public void listenerProcessQueue(@Payload String title){
        System.out.println(title);
    }
}
