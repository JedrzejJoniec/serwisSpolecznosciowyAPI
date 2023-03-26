package com.example.serwis.entity;

import lombok.*;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    private UserEntity user;
    @NonNull
    private String body;

    private Long parentId;

    private String date;

    public PostEntity(UserEntity userEntity, String postBody, Long parentId, String now) {
        this.user = userEntity;
        this.body = postBody;
        this.parentId = parentId;
        this.date = now;
    }


    public String getDate(){
        return this.date.substring(0, this.date.length() - 3);
    }

    public PostEntity(UserEntity userEntity, String body, long parentPostId) {
        this.user = userEntity;
        this.body = body;
        this.parentId = parentPostId;

    }
}
