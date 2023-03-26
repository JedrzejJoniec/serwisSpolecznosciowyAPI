package com.example.serwis.mapper;

import com.example.serwis.entity.PostEntity;
import com.example.serwis.model.Post;
import com.example.serwis.repository.PostRepository;
import com.example.serwis.service.ImageService;
import com.example.serwis.service.ReactionService;
import com.example.serwis.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PostMapper {

    @Autowired
    ImageService imageService;

    @Autowired
    ReactionService reactionService;

    @Autowired
    RelationService relationService;

    @Autowired
    PostRepository postRepository;


    public List<Post> parsePosts(List<PostEntity> postsJPA, long userId) throws IOException {
        List<Post> posts = parsePostsJPAtoPosts(postsJPA, userId);
        Collections.reverse(posts);
        relationService.deleteBlockedUsersContent(posts, userId);
        relationService.deleteBlockingUsersContent(posts, userId);
        setIsLiked(posts, userId);
        isLikedUserFollowed(posts, userId);
        return posts;
    }

    private void isLikedUserFollowed(List<Post> posts, long id) {
        posts.forEach(post -> post.getReactions().forEach(reaction -> reaction.getUser().setFollowed(relationService.isFollowed(reaction.getUser().getId(), id))));

    }
    public void setIsLiked(List<Post> posts, long id) {
        posts.forEach(post -> {
            if (reactionService.isLiked(id, post.getId())) {
                post.setLiked(true);
            }

        });
    }
    public List<Post> parsePostsJPAtoPosts(List<PostEntity> postsJPA, long userId) throws IOException {
        List<Post> posts = new ArrayList<>();
        for (PostEntity postJPA: postsJPA) {
            if(postJPA.getParentId() == null)
                posts.add(new Post(postJPA.getId(),
                        postJPA.getUser().getUsername(),
                        postJPA.getUser().getId(),
                        getComments(postJPA.getId(), userId), reactionService.getPostReactions(postJPA.getId()),
                        imageService.checkIfPostHasImage(postJPA.getId()),
                        false,
                        postJPA.getBody(), postJPA.getDate()));

        }
        return posts;
    }
    public List<Post> parsePostsJPAtoComments(List<PostEntity> postsJPA, long userId) throws IOException {
        List<Post> posts = new ArrayList<>();
        for (PostEntity postJPA: postsJPA) {
            posts.add(new Post(postJPA.getId(),
                    postJPA.getUser().getUsername(),
                    postJPA.getUser().getId(),
                    getComments(postJPA.getId(), userId), reactionService.getPostReactions(postJPA.getId()),
                    imageService.checkIfPostHasImage(postJPA.getId()),
                    false,
                    postJPA.getBody(), postJPA.getDate()));

        }
        return posts;
    }
    public Post parsePost(PostEntity postJPA, long userId) throws IOException {
        return new Post(postJPA.getId(),
                postJPA.getUser().getUsername(),
                postJPA.getUser().getId(),
                getComments(postJPA.getId(), userId), reactionService.getPostReactions(postJPA.getId()), imageService.checkIfPostHasImage(postJPA.getId()),
                reactionService.isLiked(postJPA.getUser().getId(), postJPA.getId()),postJPA.getBody(), postJPA.getDate());
    }


    public List<Post> getComments(long postId, long userId) throws IOException {
        List<PostEntity> postsJPA = postRepository.findComments(postId);
        List<Post> posts = parsePostsJPAtoComments(postsJPA, userId);
        Collections.reverse(posts);
        relationService.deleteBlockedUsersContent(posts, userId);
        relationService.deleteBlockingUsersContent(posts, userId);
        setIsLiked(posts, userId);
        isLikedUserFollowed(posts, userId);
        return posts;
    }

}
