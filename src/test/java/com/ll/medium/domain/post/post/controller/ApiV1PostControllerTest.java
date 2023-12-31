package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.standard.util.Ut.Ut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ApiV1PostControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PostService postService;

    @DisplayName("POST /api/v1/posts/temp")
    @Test
    @WithUserDetails("user1")
    void t1() throws Exception {
        String username = "user1";
        String title = "임시글";

        ResultActions resultActions = mvc
                .perform(post("/api/v1/posts/temp"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("makeTemp"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // JSON 응답 확인
                .andExpect(jsonPath("$.data.item.authorUsername").value(username))
                .andExpect(jsonPath("$.data.item.title").value(title));

        Post post = postService.findLatest().get();

        assertThat(post.getTitle()).isEqualTo(title);
    }

    @DisplayName("PUT /api/v1/posts/1")
    @Test
    @WithUserDetails("user1")
    void t2() throws Exception {
        String title = "제목 1";
        String username = "user1";
        long id = 1;

        String body = """
                $$config
                title: %s
                open: true
                $$
                """
                .formatted(title)
                .stripIndent()
                .trim();

        Map<String, String> content = Map.of(
                "body", body
        );

        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/posts/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(Ut.json.toString(content))
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("edit"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // JSON 응답 확인
                .andExpect(jsonPath("$.data.item.authorUsername").value(username))
                .andExpect(jsonPath("$.data.item.title").value(title));

        Post post = postService.findById(id).get();

        assertThat(post.getTitle()).isEqualTo(title);
    }
}
