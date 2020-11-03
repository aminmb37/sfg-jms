package com.amin.jms.listener;

import com.amin.jms.config.JmsConfig;
import com.amin.jms.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.QUEUE_NAME)
    public void listen(@Payload HelloMessage helloMessage, @Headers MessageHeaders headers, Message message) {
        System.out.println("I got a message!!!");
        System.out.println(helloMessage);
        //throw new RuntimeException();
    }

    @JmsListener(destination = JmsConfig.SEND_RECEIVE_QUEUE_NAME)
    public void listenForHello(@Payload HelloMessage helloMessage,
                               @Headers MessageHeaders headers, Message message) throws JMSException {
        HelloMessage world = HelloMessage.builder().id(UUID.randomUUID()).message("World!!").build();
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), world);
    }
}
