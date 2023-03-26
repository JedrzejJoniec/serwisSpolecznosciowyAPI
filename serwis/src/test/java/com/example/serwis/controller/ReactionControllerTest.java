package com.example.serwis.controller;

import com.example.serwis.entity.ReactionEntity;
import com.example.serwis.entity.RelationEntity;
import com.example.serwis.repository.ReactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
class ReactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReactionRepository reactionRepository;

    @Test
    @Transactional
    void addReaction() throws Exception {
        mockMvc.perform(post("/post/2/reactions")).andDo(print())
                .andExpect(status().isOk());
        List<ReactionEntity> relationFromDB = reactionRepository.findPostReactions(2L);
        assertEquals("jedo1", relationFromDB.get(0).getUser().getUsername());
    }

    @Test
    @Transactional
    void deleteReaction() throws Exception {
        mockMvc.perform(delete("/post/1/reactions")).andDo(print())
                .andExpect(status().isOk());
        List<ReactionEntity> relationFromDB = reactionRepository.findPostReactions(1L);
        assertEquals("jedo2", relationFromDB.get(0).getUser().getUsername());
    }
}