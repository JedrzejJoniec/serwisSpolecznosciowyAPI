package com.example.broker.websocket;

import com.example.broker.entity.Notification;
import com.example.broker.entity.NotificationMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;

@Component
public class MessageListenerImplementation implements MessageListener {

    private String address;
    public MessageListenerImplementation() {
    }

    public MessageListenerImplementation(String myValue) {
        this.address = myValue;
    }

    @Override
    public void onMessage(Message message) {
        String uri = address + message.getMessageProperties().getReceivedRoutingKey() + "/notification";
        RestTemplate restTemplate = new RestTemplate();

        String messagestr = new String(message.getBody());
        if (!message.getMessageProperties().getReceivedExchange().equals("message")) {
            String messageBody = messagestr.substring(0, messagestr.indexOf(";"));
            String object = messagestr.substring(messagestr.indexOf(";") + 1, messagestr.indexOf(":"));
            String postId = messagestr.substring(messagestr.indexOf(":") + 1);
            Notification notification = new Notification(message.getMessageProperties().getReceivedRoutingKey(), message.getMessageProperties().getReceivedExchange()
                    , false, messageBody, LocalDateTime.now().toString(), Integer.parseInt(postId), object);
            restTemplate.postForObject(uri, notification,String.class);
        }
        else if(message.getMessageProperties().getReceivedExchange().equals("message")){
            String body = messagestr.substring(0, messagestr.indexOf(":"));
            String author = messagestr.substring(messagestr.indexOf(":") + 1);
            NotificationMessage notificationMessage = new NotificationMessage(author,
                    body, LocalDateTime.now().toString(), false);
            uri = address + message.getMessageProperties().getReceivedRoutingKey() + "/notificationMessage";
            restTemplate.postForObject(uri, notificationMessage,String.class);
        }

    }
}
