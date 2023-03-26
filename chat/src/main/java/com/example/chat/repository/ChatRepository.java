package com.example.chat.repository;

import com.example.chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChatRepository extends MongoRepository<Chat, String> {

    @Query("{'$and':[ {'users': :#{#user1} }, {'users': :#{#user2} } ] }")
    Chat findChatByUsers(@Param("user1") String user1, @Param("user2") String user2);

    @Query("{'$and':[ {'users': :#{#username} } ] }")
    List<Chat> findUserChats(@Param("username") String username);

    @Query(value="{'$and':[ {'users': :#{#loggedInUserUsername} }, {'users': :#{#blockedUserUsername} } ] }", delete = true)
    void deleteChat(@Param("loggedInUserUsername") String loggedInUserUsername, @Param("blockedUserUsername") String blockedUserUsername);
}
