package com.example.posts.mapper;

import com.example.posts.entity.UserEntity;
import com.example.posts.model.User;
import com.example.posts.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapper {

    @Autowired
    RelationService relationService;


    public List<User> parseUsers(List<UserEntity> usersEntities, long loggedInUserId) {
        List<User> users = parseUsersEntityToUsers(usersEntities);
        users.removeIf(user -> user.getId() == loggedInUserId);
        users.forEach(user -> user.setFollowed(relationService.isFollowed(user.getId(), loggedInUserId)));
        relationService.deleteBlockingUsers(users, loggedInUserId);
        relationService.deleteBlockedUsers(users, loggedInUserId);
        return users;
    }


    public List<User> parseUsersEntityToUsers(List<UserEntity> usersEntity) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity: usersEntity) {
            users.add(new User(userEntity.getId(), userEntity.getUsername(), false));
        }
        return users;
    }
    public User parseUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getUsername(), false);
    }
}
