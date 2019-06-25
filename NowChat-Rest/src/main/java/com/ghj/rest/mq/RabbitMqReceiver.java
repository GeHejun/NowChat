package com.ghj.rest.mq;

import com.ghj.common.base.Constant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Constant.QUEUE_A)
public class RabbitMqReceiver {
    @RabbitHandler
    public void process(String content) {
    }
}
