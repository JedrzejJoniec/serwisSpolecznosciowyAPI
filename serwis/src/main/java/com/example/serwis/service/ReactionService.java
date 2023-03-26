package com.example.serwis.service;

import com.example.serwis.model.Reaction;
import com.example.serwis.entity.ReactionEntity;
import com.example.serwis.model.User;
import com.example.serwis.repository.ReactionRepository;
import com.example.serwis.entity.PostEntity;
import com.example.serwis.repository.PostRepository;
import com.example.serwis.entity.UserEntity;
import com.example.serwis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {
    @Autowired
    ReactionRepository reactionRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    public List<Reaction> parseReactions(List<ReactionEntity> reactionsJPA) {
        List<Reaction> reactions = new ArrayList<>();
        for (ReactionEntity reactionJPA: reactionsJPA) {
            reactions.add(new Reaction(reactionJPA.getId(), new User(reactionJPA.getUser().getId(), reactionJPA.getUser().getUsername(), false)
                    , reactionJPA.getPost().getId(), reactionJPA.getDate()));
        }
        return reactions;
    }

    public void addReaction(long postId, long userId) {
        PostEntity postJPA = postRepository.findPostById(postId);
        Optional<UserEntity> user = userRepository.findById(userId);
        if (reactionRepository.findPostReaction(postJPA.getId(), user.get().getId()) == null) {
            ReactionEntity reactionJPA = new ReactionEntity(postJPA, user.get(), LocalDateTime.now().toString());
            reactionRepository.save(reactionJPA);
        }
    }
    public void removeReaction(long postId, long userId) {
        reactionRepository.deleteReaction(postId, userId);
    }


    public List<Reaction> getPostReactions(long postId) {

        return parseReactions(reactionRepository.findPostReactions(postId));
    }
    public boolean isLiked(long userId, long postId) {
        boolean isLiked = false;
        List<Reaction> postReactions = getPostReactions(postId);
        for(Reaction reaction: postReactions) {
            if (reaction.getUser().getId() == userId) {
                isLiked = true;
            }
        }

        return isLiked;
    }
}
