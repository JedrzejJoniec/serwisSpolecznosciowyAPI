package com.example.posts.repository;

import com.example.posts.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @Query(value="SELECT * FROM user INNER JOIN relation ON relation.first_user_id = :loggedInUserId AND relation.relation_name = 'block' AND user.id = relation.second_user_id", nativeQuery = true)
    List<UserEntity> findBlockedUsers(@Param("loggedInUserId") Long loggedInUserId);

    @Query(value="SELECT * FROM user INNER JOIN relation ON relation.first_user_id = :loggedInUserId AND relation.relation_name = 'follow' AND user.id = relation.second_user_id", nativeQuery = true)
    List<UserEntity> findFollowedUsers(@Param("loggedInUserId") Long loggedInUserId);
    @Query(value="SELECT * FROM user WHERE Username LIKE CONCAT('%',:username,'%');", nativeQuery = true)
    List<UserEntity> findUsersByUsername(@Param("username") String username);


}
