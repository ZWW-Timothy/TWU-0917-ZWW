package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerDatabaseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    @BeforeEach
    void should_reset_for_test() {
        voteRepository.deleteAll();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        UserEntity userEntity1 = UserEntity.builder()
                .userId(1)
                .userName("person1")
                .userGender("male")
                .userAge(30)
                .userEmail("p1@m.com")
                .userPhoneNum("12312345678")
                .userVoteNum(10)
                .build();
        userRepository.save(userEntity1);
        UserEntity userEntity2 = UserEntity.builder()
                .userId(2)
                .userName("person2")
                .userGender("female")
                .userAge(20)
                .userEmail("p2@m.com")
                .userPhoneNum("12412345678")
                .userVoteNum(10)
                .build();
        userRepository.save(userEntity2);
        RsEventEntity rsEventEntity1 = RsEventEntity.builder()
                .rsEventId(1)
                .rsEventName("事件1")
                .rsEventKeyword("无")
                .userId(1)
                .build();
        rsEventRepository.save(rsEventEntity1);
        RsEventEntity rsEventEntity2 = RsEventEntity.builder()
                .rsEventId(2)
                .rsEventName("事件2")
                .rsEventKeyword("无")
                .userId(2)
                .build();
        rsEventRepository.save(rsEventEntity2);
    }

    @Test
    void should_vote_if_num_enough() throws Exception {

        VoteDto voteDto = VoteDto.builder()
                .rsEventId(1)
                .userId(1)
                .voteNum(1)
                .voteTime(null)
                .build();

       String voteJson = objectMapper.writeValueAsString(voteDto);

        mockMvc.perform(post("/rsDatabase/vote/1")
                .content(voteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_vote_if_num_not_enough() throws Exception {

        VoteDto voteDto = VoteDto.builder()
                .rsEventId(1)
                .userId(1)
                .voteNum(11)
                .voteTime(null)
                .build();

        String voteJson = objectMapper.writeValueAsString(voteDto);

        mockMvc.perform(post("/rsDatabase/vote/1")
                .content(voteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
