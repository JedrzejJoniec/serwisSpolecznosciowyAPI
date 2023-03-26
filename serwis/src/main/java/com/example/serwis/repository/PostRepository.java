package com.example.serwis.repository;

import com.example.serwis.entity.PostEntity;
import com.example.serwis.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {


    @Query(value = "SELECT * FROM post INNER JOIN relation ON relation.second_user_id=post.user_id " +
            "AND relation.first_user_id = :id AND relation.relation_name = 'follow' INNER JOIN user ON user.id = post.user_id;", nativeQuery = true)
    List<PostEntity> findPostsFollowedUsers(@Param("id") Long id);
    @Query(value = "SELECT * FROM post WHERE post.user_id = :id GROUP BY post.id;", nativeQuery = true)
    List<PostEntity> findPostsByUserId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reaction  WHERE reaction.post_id = :id", nativeQuery = true)
    void deleteReactionByPostId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM image  WHERE image.post_id = :id", nativeQuery = true)
    void deleteImageByPostId(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM post WHERE post.id = :id", nativeQuery = true)
    void deletePostById(@Param("id") Long id);

    @Query(value = "SELECT * FROM post WHERE post.id = :id", nativeQuery = true)
    PostEntity findPostById(@Param("id") Long id);

    @Query(value = "SELECT * FROM post WHERE EXISTS (SELECT * FROM reaction WHERE reaction.post_id = post.id AND reaction.user_id = :userId) GROUP BY post.id", nativeQuery = true)
    List<PostEntity> findLikedPosts(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM post INNER JOIN user ON user.id=post.user_id WHERE post.parent_id= :postId GROUP BY post.id;" , nativeQuery = true)
    List<PostEntity> findComments(@Param("postId") Long postId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM post WHERE post.parent_id = :id", nativeQuery = true)
    void deletePostComments(@Param("id") Long id);


    @Query(value="SELECT * FROM post WHERE body LIKE CONCAT('%',:text,'%');", nativeQuery = true)
    List<PostEntity> findPostsByText(@Param("text") String text);
}
