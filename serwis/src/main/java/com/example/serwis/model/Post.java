package com.example.serwis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.core.io.ByteArrayResource;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class Post {
    private long id;
    private String username;
    private long userId;
    private List<Post> comments;
    private List<Reaction> reactions;
    private boolean hasImage;
    private boolean isLiked;
    private String body;
    private String date;
}
