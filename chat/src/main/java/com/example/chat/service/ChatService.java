package com.example.chat.service;

import com.example.chat.entity.Chat;
import com.example.chat.repository.ChatRepository;
import com.example.chat.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    public void addMessage(String author, String receiver, String text) {
        Chat chat = getChat(author, receiver);
        Message message = new Message(author, text,  LocalDateTime.now().toString(), false);
        message.setDate(message.getDate().replace("T", " "));
        message.setDate(message.getDate().substring(0, message.getDate().length() -13));
        if (chat.getId() != null) {
            chat.getMessages().add(message);
            chatRepository.save(chat);
        }
        else {
            List<String> users = new ArrayList<>();
            List<Message> messages = new ArrayList<>();
            users.add(author);
            users.add(receiver);
            messages.add(message);
            chatRepository.save(new Chat(users, messages));
        }


    }
    public List<Message> getUnSeenMessages(String username) {
        List<Chat> chats = chatRepository.findUserChats(username);
        List<Message> messages = new ArrayList<>();
        chats.forEach(chat -> chat.getMessages().forEach(message -> {
            if(!message.isSeen() && !message.getAuthor().equals(username)){
                messages.add(message);
            }})
        );
        return messages;
    }
    public Chat getChat(String member1, String member2) {
        Chat chat = chatRepository.findChatByUsers(member1, member2);
        if (chat != null) {
            return chatRepository.findChatByUsers(member1, member2);
        }
       return new Chat();
    }
    public void setMessagesSeen(String author, String receiver) {
        Chat chat = getChat(author, receiver);
        chat.getMessages().forEach(message -> message.setSeen(true));
        chatRepository.save(chat);
    }
    public List<String> getContacts(String loggedInUserUsername,String username) {
        List<Chat> chats = chatRepository.findUserChats(username);
        List<String> contacts = new ArrayList<>();
        chats.forEach(chat -> chat.getUsers().forEach(user -> {
            if(!user.equals(loggedInUserUsername)){
                contacts.add(user);
            }})
        );
        return contacts;
    }
    public void deleteChat(String loggedInUserUsername, String blockedUserUsername) {
        chatRepository.deleteChat(loggedInUserUsername, blockedUserUsername);
    }
}
