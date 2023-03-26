package com.example.serwis.repository;

import com.example.serwis.entity.ImageEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @Test
    void shouldFindImageByPostId() {
        ImageEntity imageEntity = imageRepository.findImageByPostId(1L);
        assertEquals(imageEntity.getImageName(), "nazwa zdjecia");
    }

    @Test
    void shouldFindIfPostHasImage() {
        long postHasImage = imageRepository.findIfPostHasImage(1L);
        assertEquals(postHasImage, 1L);
    }
}