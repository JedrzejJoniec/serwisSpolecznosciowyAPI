package com.example.broker.service;

import com.example.broker.entity.Notification;
import com.example.broker.entity.NotificationMessage;
import com.example.broker.repository.NotificationRepository;
import com.example.broker.websocket.MessageListenerImplementation;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.amqp.core.Binding.DestinationType.QUEUE;

@Service
public class BrokerService {

    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${spring.rabbitmq.addresses}")
    private String rabbitUri;

    @Value("${spring.address}")
    private String address;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private AmqpAdmin amqpAdmin;

    public void sendNotification(Notification message, String username) throws Exception {
        message.setDate(message.getDate().replace("T", " "));
        message.setDate(message.getDate().substring(0, message.getDate().length() -13));
        template.convertAndSend("/topic/" + username, message);
    }


    public void sendNotificationMessage(NotificationMessage message, String username) throws Exception {
        message.setDate(message.getDate().replace("T", " "));
        message.setDate(message.getDate().substring(0, message.getDate().length() -13));
        template.convertAndSend("/topic/" + username, message);
    }


    public void addNotification( int id, String type, String username,  String author, String object) {
        rabbitTemplate.convertAndSend(type, username, author + ";" + object + ":"+ id);
        Notification notification = new Notification(username, type, false, author, LocalDateTime.now().toString(), id, object);
        notificationRepository.save(notification);
    }
    public void sendMessageNotification(String text,  String author, String receiver) {
        String type = "message";
        rabbitTemplate.convertAndSend(type, receiver, text + ":" + author);
    }

    public List<Notification> getNotifications(String username) {
        List<Notification> notifications = notificationRepository.findUserNotifications(username);
        Collections.reverse(notifications);
        notifications.forEach(notification -> notification.setDate(notification.getDate().substring(0, notification.getDate().length() -3)));
        return notifications;

    }
    public void setNotificationsSeen(String username) {
        notificationRepository.setUserNotificationsSeen(username);

    }
    public void deleteNotifications(String loggedInUserUsername, String blockedUserUsername) throws Exception {
        notificationRepository.deleteNotifications(loggedInUserUsername, blockedUserUsername);
    }

    public void createQueue(String username) {
        Queue queue = new Queue(username, true, false, false);
        amqpAdmin.declareQueue(queue);
        Binding binding = new Binding(username, QUEUE, "like", username, null);
        amqpAdmin.declareBinding(binding);
        binding = new Binding(username, QUEUE, "comment", username, null);
        amqpAdmin.declareBinding(binding);
        binding = new Binding(username, QUEUE, "message", username, null);
        amqpAdmin.declareBinding(binding);
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.addQueues(queue);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        //connectionFactory.setHost("rabbit");
        //connectionFactory.setPort(5672);
        if (!rabbitUri.equals("")) {
            connectionFactory.setUri(rabbitUri);
        }

        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setMessageListener(new MessageListenerImplementation(address));
        simpleMessageListenerContainer.afterPropertiesSet();
        simpleMessageListenerContainer.start();
    }
}
