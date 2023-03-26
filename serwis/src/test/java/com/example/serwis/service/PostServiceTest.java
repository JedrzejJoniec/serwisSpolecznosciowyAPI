package com.example.serwis.service;

import com.example.serwis.entity.UserEntity;
import com.example.serwis.model.Post;
import com.example.serwis.repository.PostRepository;
import com.example.serwis.repository.RelationRepository;
import com.example.serwis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    RelationRepository relationRepositoryMock;

    @InjectMocks
    PostService postService;

  /*  @Test
    void shouldDeleteBlockedUsersPosts() {
        when(relationRepositoryMock.findBlockedUsersId(1L)).thenReturn(List.of(2L));
        List<Post> posts = prepareMockData();
        postService.deleteBlockedUsersPosts(posts, 1L);
        assertEquals(posts.size(), 1);
    }
    @Test
    void shouldDeleteBlockedUsersComments() {
        //given
        when(relationRepositoryMock.findBlockedUsersId(1L)).thenReturn(List.of(4L));
        List<Post> posts = prepareMockData();
        //when
        postService.deleteBlockedUsersComments(posts, 1L);
        //then
        assertEquals(posts.get(0).getComments().size(), 1);
    }
*/
    private List<Post> prepareMockData() {
        List<Post> posts = new LinkedList<>();
        posts.add(new Post(1L, "jedo1", 1L, new LinkedList<>(Arrays.asList(new Post(3L, "jedo3", 3L, new ArrayList<>(),
                        new ArrayList<>(), false, false,"jedo3witam", LocalDateTime.now().toString()),
                new Post(4L, "jedo4", 4L, new ArrayList<>(),new ArrayList<>(), false, false,"jedo4witam", LocalDateTime.now().toString()))), new ArrayList<>(), false, false,"jedo1witam", LocalDateTime.now().toString()));
        posts.add(new Post(2L, "jedo2", 2L, new ArrayList<>(), new ArrayList<>(), false, false,"jedo2witam", LocalDateTime.now().toString()));
        return posts;
    }


}