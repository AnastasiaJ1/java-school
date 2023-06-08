package com.digdes.school.project.amqp;

import com.digdes.school.project.impl.EmailServiceImpl;
import com.digdes.school.project.model.EmailContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import java.io.IOException;

@Configuration
public class MessageConsumer {
    public MessageConsumer(RabbitTemplate rabbitTemplate, EmailServiceImpl emailService) {
        this.rabbitTemplate = rabbitTemplate;
        this.emailService = emailService;
    }

    private final RabbitTemplate rabbitTemplate;
    private final EmailServiceImpl emailService;
    @RabbitListener(queues = "queue")
    public void receiveMessage(Message message) throws IOException, MessagingException {
        ObjectMapper mapper = new ObjectMapper();
        EmailContext emailContext = mapper.readValue(message.getBody(), EmailContext.class);
        emailService.sendMail(emailContext);
    }

}
