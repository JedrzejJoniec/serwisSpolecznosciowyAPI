package com.example.serwis.repository;


import com.example.serwis.entity.ReactionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReactionRepositoryTest {
    @Autowired
    ReactionRepository reactionRepository;

    @Test
    void shouldFindUserReaction() {
        ReactionEntity reactionEntity = reactionRepository.findPostReaction(1L, 2L);
        assertEquals(reactionEntity.getUser().getUsername(), "jedo2");
    }

    @Test
    void shouldFindPostReactions() {
        List<ReactionEntity> reactionEntities = reactionRepository.findPostReactions(1L);
        assertEquals(reactionEntities.get(0).getUser().getUsername(), "jedo2");
    }

    @Test
    void shouldFindPostReaction() {
        ReactionEntity reactionEntity = reactionRepository.findPostReaction(1L, 2L);
        assertEquals(reactionEntity.getUser().getUsername(), "jedo2");
    }
}