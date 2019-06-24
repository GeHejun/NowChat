package com.ghj.rest.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitMqConfig.QUEUE_A)
public class RabbitMqReceiver {
    @RabbitHandler
    public void process(String content) {
    }
}
