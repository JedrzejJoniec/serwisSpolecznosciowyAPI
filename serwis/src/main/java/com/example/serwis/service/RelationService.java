package com.example.serwis.service;

import com.example.serwis.entity.PostEntity;
import com.example.serwis.model.Post;
import com.example.serwis.model.Relation;
import com.example.serwis.entity.RelationEntity;
import com.example.serwis.model.User;
import com.example.serwis.repository.RelationRepository;
import com.example.serwis.entity.UserEntity;
import com.example.serwis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RelationService {
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    UserRepository userRepository;


    public boolean isFollowed(long secondUserId, long userLoggedInId) {
        if (relationRepository.findUserRelation(userLoggedInId, secondUserId) != null) {
            return relationRepository.findUserRelation(userLoggedInId, secondUserId).getRelationName().equals("follow");
        }
        return false;
    }

    public void followOrBlock(long secondUserId, long userLoggedInId, String relationName) {
        if (relationRepository.findUserRelation(userLoggedInId, secondUserId) == null) {
            RelationEntity relation = new RelationEntity(userRepository.findById(userLoggedInId).get(), secondUserId, relationName);
            relationRepository.save(relation);

        } else if (relationRepository.findUserRelation(userLoggedInId, secondUserId).getRelationName().equals("follow") && relationName.equals("block")) {
            RelationEntity relation = relationRepository.findUserRelation(userLoggedInId, secondUserId);
            relation.setRelationName("block");
            relationRepository.save(relation);
        }
    }
    public void unfollowOrUnblock(long loggedInUserId, long secondUserId) {
        relationRepository.deleteRelation(loggedInUserId, secondUserId);
    }
    public void deleteBlockingUsers(List<User> users, long id) {
        List<Long> blockedUsers = relationRepository.findBlockingUsersId(id);
        users.removeIf(user -> blockedUsers.contains(user.getId()));
    }
    public void deleteBlockedUsers(List<User> users, long id) {
        List<Long> blockedUsers = relationRepository.findBlockedUsersId(id);
        users.removeIf(user -> blockedUsers.contains(user.getId()));
    }

    public void deleteBlockedUsersContent(List<Post> posts, long userId) {
        deleteBlockedUsersPosts(posts, userId);
        deleteBlockedUsersComments(posts, userId);
        deleteBlockedUsersLikes(posts, userId);
    }


    public void deleteBlockingUsersContent(List<Post> posts, long userId) {
        deleteBlockingUsersPosts(posts, userId);
        deleteBlockingUsersComments(posts, userId);
        deleteBlockingUsersLikes(posts, userId);

    }


    private void deleteBlockedUsersPosts(List<Post> posts, long id) {
        List<Long> blockedUsers = relationRepository.findBlockedUsersId(id);
        posts.removeIf(post -> blockedUsers.contains(post.getUserId()));
    }
    private void deleteBlockedUsersLikes(List<Post> posts, long id) {
        List<Long> blockedUsers = relationRepository.findBlockedUsersId(id);
        posts.forEach(post -> post.getReactions().removeIf(reaction -> blockedUsers.contains(reaction.getUser().getId())));
    }
    private void deleteBlockedUsersComments(List<Post> posts, long id) {
        List<Long> blockedUsers = relationRepository.findBlockedUsersId(id);
        posts.forEach(post -> post.getComments().removeIf(comment -> blockedUsers.contains(comment.getUserId())));
    }

    private void deleteBlockingUsersPosts(List<Post> posts, long id) {
        List<Long> blockedUsers = relationRepository.findBlockingUsersId(id);
        posts.removeIf(post -> blockedUsers.contains(post.getUserId()));
    }
    private void deleteBlockingUsersLikes(List<Post> posts, long id) {
        List<Long> blockedUsers = relationRepository.findBlockingUsersId(id);
        posts.forEach(post -> post.getReactions().removeIf(reaction -> blockedUsers.contains(reaction.getUser().getId())));
    }
    private void deleteBlockingUsersComments(List<Post> posts, long id) {
        List<Long> blockedUsers = relationRepository.findBlockingUsersId(id);
        posts.forEach(post -> post.getComments().removeIf(comment -> blockedUsers.contains(comment.getUserId())));
    }

}
