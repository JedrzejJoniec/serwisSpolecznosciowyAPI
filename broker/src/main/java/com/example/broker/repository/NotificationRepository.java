package com.example.broker.repository;

import com.example.broker.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    @Query(value = "SELECT * FROM notification WHERE notification.username = :username GROUP BY notification.id;", nativeQuery = true)
    List<Notification> findUserNotifications(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE notification SET seen = true WHERE username = :username", nativeQuery = true)
    void setUserNotificationsSeen(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM notification WHERE username = :loggedInUserUsername AND author = :blockedUserUsername", nativeQuery = true)
    void deleteNotifications(@Param("loggedInUserUsername") String loggedInUserUsername, @Param("blockedUserUsername") String blockedUserUsername);
}




