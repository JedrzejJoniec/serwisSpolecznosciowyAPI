package com.example.posts.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "profile_image")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    private UserEntity user;

    @NonNull
    private String imageName;
}
