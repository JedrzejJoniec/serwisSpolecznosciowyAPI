package com.example.serwis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Image {
    private long id;

    private long postId;

    private String imageName;


}
