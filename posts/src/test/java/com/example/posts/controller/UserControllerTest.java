package com.example.posts.controller;

import com.example.posts.entity.UserEntity;
import com.example.posts.model.User;
import com.example.posts.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("jedo1")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void getUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String users = mvcResult.getResponse().getContentAsString();
        List<User> myObjects = Arrays.asList(objectMapper.readValue(users, User[].class));
        List<UserEntity> usersFromDB = userRepository.findAll();
        assertEquals(myObjects.get(0).getUsername(), usersFromDB.get(1).getUsername());
    }

    @Test
    void searchUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/search/users/jedo2")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        String users = mvcResult.getResponse().getContentAsString();
        List<User> myObjects = Arrays.asList(objectMapper.readValue(users, User[].class));
        List<UserEntity> usersFromDB = userRepository.findUsersByUsername("jedo2");
        assertEquals(myObjects.get(0).getUsername(), usersFromDB.get(0).getUsername());
    }

    @Test
    @Transactional
    void changePassword() throws Exception {
        mockMvc.perform(put("/passwordChange").content("haslotest")).andDo(print())
                .andExpect(status().isOk());
        String userPasswordFromDB = userRepository.findById(1L).get().getPassword();
        assertTrue(passwordEncoder.matches("haslotest", userPasswordFromDB));
    }

    @Test
    void getLoggedInUser() throws Exception {
        User user = objectMapper.readValue(mockMvc.perform(get("/loggedInUser")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), User.class);
        assertEquals("jedo1", user.getUsername());
    }
    @Test
    void getBlockedUsers() throws Exception {
        mockMvc.perform(get("/blockedUsers")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        List<UserEntity> usersFromDB = userRepository.findBlockedUsers(2L);
        assertEquals("jedo3", usersFromDB.get(0).getUsername());
    }
}