package com.example.serwis.controller;

import com.example.serwis.entity.RelationEntity;
import com.example.serwis.entity.UserEntity;
import com.example.serwis.repository.RelationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("jedo1")
class RelationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RelationRepository relationRepository;

    @Test
    @Transactional
    void followOrBlock() throws Exception {
        mockMvc.perform(post("/users/3").content("follow")).andDo(print())
                .andExpect(status().isOk());
        RelationEntity relationFromDB = relationRepository.findUserRelation(1L, 3L);
        assertEquals("follow", relationFromDB.getRelationName());
    }

    @Test
    @Transactional
    void unfollowOrUnblock() throws Exception {
        mockMvc.perform(delete("/users/3")).andDo(print())
                .andExpect(status().isOk());
        List<Long> relationFromDB = relationRepository.findBlockedUsersId(1L);
        assertEquals(0, relationFromDB.size());
    }

}