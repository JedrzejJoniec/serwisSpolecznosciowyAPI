package com.example.serwis.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "relation")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    private UserEntity firstUser;

    @NonNull
    private long secondUserId;

    @NonNull
    private String relationName;
}
