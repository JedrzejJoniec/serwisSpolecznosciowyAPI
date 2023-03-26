package com.example.chat.controller;

import com.example.chat.pojo.Message;
import com.example.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    ChatService chatService;


    @PostMapping("/addMessage")
    public void addMessage(@RequestParam("author") String author, @RequestParam("receiver") String receiver, @RequestParam("text") String text) {

    }

    @PostMapping("/setMessagesSeen")
    public void setMessagesSeen(@RequestParam("author") String author, @RequestParam("receiver") String receiver) {
        chatService.setMessagesSeen(author, receiver);
    }

    @GetMapping("/getUnSeenMessages")
    public List<Message> getUnSeenMessages(@RequestParam("username") String username) {
        List <Message> chat = chatService.getUnSeenMessages(username);
        Collections.reverse(chat);
        return chat;
    }
    @DeleteMapping("/deleteChat/{blockedUserUsername}")
    public void deleteChat(Authentication authentication, @PathVariable("blockedUserUsername") String blockedUserUsername) {
        chatService.deleteChat(authentication.getName(), blockedUserUsername);
    }
    @GetMapping("/chat")
    public List<Message> getChat(@RequestParam("member1") String member1, @RequestParam("member2") String member2) {
        return chatService.getChat(member1, member2).getMessages();
    }

    @PostMapping("/rest/{receiver}/addMessage")
    public String sendMessage(@PathVariable String receiver, @RequestParam("author") String author, @RequestBody String text) throws Exception {
        chatService.addMessage(author, receiver, text);
        return "hello";
    }
    @GetMapping("/contacts/{username}")
    public List<String> getContacts(@PathVariable String username, Authentication authentication) {
        return chatService.getContacts(authentication.getName(), username);
    }
}
