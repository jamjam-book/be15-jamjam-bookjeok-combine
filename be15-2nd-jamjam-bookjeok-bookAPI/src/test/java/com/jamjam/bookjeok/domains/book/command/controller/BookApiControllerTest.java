package com.jamjam.bookjeok.domains.book.command.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookApiControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final String BASE_URL = "/api/v1";

    @DisplayName("도서 정보 insert를 위한 API 호출 테스트")
    @Test
    void getBookInfo() throws Exception {

        // given
        String isbn = "9791190090261";

        // then
        mvc.perform(post(BASE_URL + "/book/api/{isbn}", isbn)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }
}