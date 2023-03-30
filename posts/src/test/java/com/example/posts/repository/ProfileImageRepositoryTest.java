package com.example.posts.repository;

import com.example.posts.entity.ProfileImageEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileImageRepositoryTest {

    @Autowired
    ProfileImageRepository profileImageRepository;

    @Test
    void findImageByUserId() {
        ProfileImageEntity profileImageEntity = profileImageRepository.findImageByUserId(2L);
        assertEquals(profileImageEntity.getImageName(), "profilowe test");
    }
}