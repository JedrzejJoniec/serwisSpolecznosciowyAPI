package com.example.chat.entity;

import com.example.chat.pojo.Message;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Chats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Chat {

    @Id
    private String id;

    private List<String> users;

    private List<Message> messages;


    public Chat(List<String> users, List<Message> messages) {
        this.users = users;
        this.messages = messages;
    }
}
