package com.example.posts.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Relation {

    private long id;

    private long firstUserId;

    private long secondUserId;

    private String secondUserUsername;

    private String relationName;

}
