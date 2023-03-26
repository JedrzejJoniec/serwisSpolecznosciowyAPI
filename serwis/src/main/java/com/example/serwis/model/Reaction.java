package com.example.serwis.model;

import lombok.*;

import java.time.LocalDateTime;


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