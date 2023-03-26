package com.example.serwis.mapper;

import com.example.serwis.entity.UserEntity;
import com.example.serwis.model.User;
import com.example.serwis.repository.UserRepository;
import com.example.serwis.service.RelationService;
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
