package com.amin.jms.sender;

import com.amin.jms.config.JmsConfig;
import com.amin.jms.model.HelloMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        System.out.println("I'm sending a message.");
        HelloMessage helloMessage = HelloMessage.builder().id(UUID.randomUUID()).message("Hello World!").build();
        jmsTemplate.convertAndSend(JmsConfig.QUEUE_NAME, helloMessage);
        System.out.println("Message sent!");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        System.out.println("Sending Hello!");
        HelloMessage hello = HelloMessage.builder().id(UUID.randomUUID()).message("Hello").build();
        Message response = jmsTemplate.sendAndReceive(JmsConfig.SEND_RECEIVE_QUEUE_NAME, session -> {
            try {
                Message message = session.createTextMessage(objectMapper.writeValueAsString(hello));
                message.setStringProperty("_type", "com.amin.jms.model.HelloMessage");
                return message;
            } catch (JsonProcessingException e) {
                throw new JMSException("Error in creating the text message!");
            }
        });
        System.out.println("response: " + (response != null ? response.getBody(String.class) : ""));
    }
}
