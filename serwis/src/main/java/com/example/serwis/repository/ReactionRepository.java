package com.example.serwis.repository;

import com.example.serwis.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reaction WHERE reaction.post_id = :postId AND reaction.user_id = :userId", nativeQuery = true)
    void deleteReaction(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query(value = "SELECT * FROM reaction WHERE reaction.post_id = :postId", nativeQuery = true)
    List<ReactionEntity> findPostReactions(@Param("postId") Long postId);
    @Query(value = "SELECT * FROM reaction WHERE reaction.post_id = :postId AND reaction.user_id = :userId", nativeQuery = true)
    ReactionEntity findPostReaction(@Param("postId") Long postId, @Param("userId") Long userId);


}