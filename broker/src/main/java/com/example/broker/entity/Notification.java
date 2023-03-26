package com.example.broker.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notification{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String username;

    @NonNull
    private String type;

    @NonNull
    private boolean seen;

    @NonNull
    private String author;

    @NonNull
    private String date;

    @NonNull
    private int postId;

    private String typeOfNotificatedPost;

    public Notification(String username, String type, boolean seen, String author, String now, int postId, String object) {
        this.username = username;
        this.type = type;
        this.seen = seen;
        this.author = author;
        this.date = now;
        this.postId = postId;
        this.typeOfNotificatedPost = object;
    }
}
