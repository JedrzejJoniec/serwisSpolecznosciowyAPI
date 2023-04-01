package com.example.broker.controler;

import com.example.broker.entity.Notification;
import com.example.broker.entity.NotificationMessage;
import com.example.broker.repository.NotificationRepository;
import com.example.broker.service.BrokerService;
import com.example.broker.websocket.MessageListenerImplementation;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.amqp.core.Binding.DestinationType.QUEUE;

@RestController
public class BrokerController {


    @Autowired
    BrokerService brokerService;


    @PostMapping("/{username}/notification")
    public void sendNotification(@RequestBody Notification message, @PathVariable("username") String username) throws Exception {
        brokerService.sendNotification(message, username);
    }

    @PostMapping("/{username}/notificationMessage")
    public void sendNotificationMessage(@RequestBody NotificationMessage message, @PathVariable("username") String username) throws Exception {
        brokerService.sendNotificationMessage(message, username);
    }

    @PostMapping("/addNotification")
    public void addNotification(@RequestParam("id") int id, @RequestParam("type") String type,
                              @RequestParam("username") String username, @RequestParam("author") String author,  @RequestParam("object") String object) {
        brokerService.addNotification(id, type, username, author, object);
    }
    @PostMapping("/addMessageNotification")
    public void sendMessageNotification(@RequestParam("text") String text, @RequestParam("author") String author, @RequestParam("receiver") String receiver) {
        brokerService.sendMessageNotification(text, author, receiver);
    }

    @GetMapping("/notifications")
    public List<Notification> getNotifications(@RequestParam("username") String username) {
        return brokerService.getNotifications(username);

    }
    @PostMapping("/notifications")
    public void setNotificationsSeen(@RequestParam("username") String username) {
        brokerService.setNotificationsSeen(username);

    }
    @DeleteMapping("/deleteNotifications/{blockedUser}")
    public void deleteUserNotifications(Authentication authentication, @PathVariable("blockedUser") String blockedUserUsername) throws Exception {
        brokerService.deleteUserNotifications(authentication.getName(),blockedUserUsername);
    }


    @PostMapping("/createQueue")
    public void createQueue(@RequestParam("username") String username) {
        brokerService.createQueue(username);
    }


}
