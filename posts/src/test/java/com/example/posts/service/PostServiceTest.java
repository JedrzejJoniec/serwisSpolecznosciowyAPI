package com.example.posts.service;

import com.example.posts.model.Post;
import com.example.posts.repository.RelationRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

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