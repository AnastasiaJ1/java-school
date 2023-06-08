package com.digdes.school.project.amqp;

import com.digdes.school.project.model.EmailContext;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Value("${exchange}")
    private String exchange;
    @Value("${routingkey}")
    private String routingkey;
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendMessage(EmailContext emailContext){
        rabbitTemplate.convertAndSend(exchange, routingkey, emailContext);
    }
}
