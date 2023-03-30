package com.example.posts.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class Reaction {

    private long id;

    private User user;

    private long postId;

    private String date;

}