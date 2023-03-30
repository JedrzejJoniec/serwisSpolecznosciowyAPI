package com.example.posts.repository;

import com.example.posts.entity.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RelationRepository extends JpaRepository<RelationEntity, Long> {
    @Query(value="SELECT relation.second_user_id FROM relation WHERE relation.first_user_id = :id AND relation.relation_name = 'block' ", nativeQuery = true)
    List<Long> findBlockedUsersId(@Param("id") Long id);

    @Query(value="SELECT * FROM relation WHERE relation.first_user_id = :loggedInUserId AND relation.second_user_id = :secondUserId ", nativeQuery = true)
    RelationEntity findUserRelation(@Param("loggedInUserId") Long loggedInUserId, @Param("secondUserId") Long secondUserId);

    @Query(value="SELECT relation.first_user_id FROM relation WHERE relation.second_user_id = :id AND relation.relation_name = 'block' ", nativeQuery = true)
    List<Long> findBlockingUsersId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM relation WHERE relation.first_user_id = :loggedInUserId AND relation.second_user_id = :secondUserId", nativeQuery = true)
    void deleteRelation(@Param("loggedInUserId") Long loggedInUserId, @Param("secondUserId") Long secondUserId);

}
