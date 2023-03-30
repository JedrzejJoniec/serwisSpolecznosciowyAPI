package com.example.posts.repository;

import com.example.posts.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository  extends JpaRepository<ImageEntity, Long> {
    @Query(value = "SELECT * FROM image WHERE image.post_id= :postId" , nativeQuery = true)
    ImageEntity findImageByPostId(@Param("postId") Long postId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE serwis.image SET image_name = :name WHERE post_id= :id", nativeQuery = true)
    void changePostImage(@Param("id") Long id, @Param("name") String name);
    @Query(value = "SELECT count(*) FROM image WHERE post_id = :postId" , nativeQuery = true)
    long findIfPostHasImage(@Param("postId") Long postId);
}
