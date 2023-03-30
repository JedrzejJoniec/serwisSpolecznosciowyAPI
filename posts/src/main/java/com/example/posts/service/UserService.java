package com.example.posts.service;

import com.example.posts.entity.UserEntity;
import com.example.posts.mapper.UserMapper;
import com.example.posts.model.User;

import com.example.posts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RelationService relationService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getUsers(long loggedInUserId) {
        List<UserEntity> usersEntities = userRepository.findAll();
        return userMapper.parseUsers(usersEntities, loggedInUserId);
    }
    public User getUser(String username, long loggedInUserId) {
        User user = userMapper.parseUser((userRepository.findByUsername(username)));
        user.setFollowed(relationService.isFollowed(user.getId(), loggedInUserId));
        return user;
    }

    public List<User> searchUsers(String username, long loggedInUserId) {
        List<UserEntity> usersEntities = userRepository.findUsersByUsername(username);
        return userMapper.parseUsers(usersEntities, loggedInUserId);
    }
    public long getUserIdByUsername(String username) {
        return userRepository.findByUsername(username).getId();
    }

    public UserEntity getUserById(long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        return userEntityOptional.get();
    }
    public void addUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public User changePassword(long id, String password) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Not found: ");
        }
        user.get().setPassword(passwordEncoder.encode(password));
        return userMapper.parseUser(userRepository.save(user.get()));
    }
    public List<User> getBlockedUsers(long id) {
        return userMapper.parseUsersEntityToUsers(userRepository.findBlockedUsers(id));
    }

    public List<User> getFollowedUsers(long loggedInUserId){
        List<UserEntity> usersEntities = userRepository.findFollowedUsers(loggedInUserId);
        return userMapper.parseUsers(usersEntities, loggedInUserId);
    }

}
