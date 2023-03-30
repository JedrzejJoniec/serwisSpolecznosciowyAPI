package com.example.posts.repository;

import com.example.posts.entity.RelationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RelationRepositoryTest {
    @Autowired
    RelationRepository relationRepository;

    @Test
    void findBlockedUsersId() {
        List<Long> blockedUsers = relationRepository.findBlockedUsersId(2L);
        assertEquals(blockedUsers.size(), 1L);
    }

    @Test
    void findUserRelation() {
        RelationEntity userRelation = relationRepository.findUserRelation(1L, 2L);
        assertEquals(userRelation.getRelationName(), "follow");
    }
    @Test
    @Transactional
    void shouldDeleteRelation() {
        relationRepository.deleteRelation(2L, 3L);
        assertEquals(0, relationRepository.findBlockedUsersId(2L).size());
    }
}