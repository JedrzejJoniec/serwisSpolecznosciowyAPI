package com.example.posts.repository;

import com.example.posts.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findUsersByUsername() {
        List<UserEntity> userEntities = userRepository.findUsersByUsername("jedo1");
        assertEquals(userEntities.get(0).getUsername(), "jedo1");
    }

    @Test
    void findBlockedUsers() {
        List<UserEntity> userEntities = userRepository.findBlockedUsers(2L);
        assertEquals(userEntities.get(0).getUsername(), "jedo3");
    }
}