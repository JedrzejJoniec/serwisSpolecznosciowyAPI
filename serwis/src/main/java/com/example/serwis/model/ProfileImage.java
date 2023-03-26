package com.example.serwis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProfileImage {
    private long id;

    private long userId;

    private String imageName;
}
