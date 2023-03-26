package com.example.broker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class NotificationMessage{
    private String author;
    private String text;
    private String date;
    private boolean seen;
}

