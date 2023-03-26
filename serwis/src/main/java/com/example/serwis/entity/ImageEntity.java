package com.example.serwis.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    private PostEntity post;

    @NonNull
    private String imageName;
}
