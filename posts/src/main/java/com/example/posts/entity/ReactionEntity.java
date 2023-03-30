package com.example.posts.entity;

import lombok.*;


import javax.persistence.*;

@Entity
@Table(name = "reaction")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    private PostEntity post;

    @NonNull
    @ManyToOne
    private UserEntity user;

    private String date;

    public ReactionEntity(PostEntity postJPA, UserEntity userEntity, String now) {
        this.post = postJPA;
        this.user = userEntity;
        this.date = now;
    }

    public String getDate(){
        return this.date.substring(0, date.length() - 3);
    }
}
