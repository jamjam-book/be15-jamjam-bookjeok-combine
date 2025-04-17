package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.InterestAuthorRequest;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AlreadyInterestedAuthorException;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AuthorNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class InterestAuthorCommandServiceImplTest {

    @Autowired
    private InterestAuthorCommandService interestAuthorCommandService;

    @DisplayName("관심 작가 등록하기")
    @Test
    void createInterestAuthorTest(){
        Long memberUid = 2L;

        String authorName = "공지영";
        InterestAuthorRequest request = new InterestAuthorRequest(authorName, memberUid);
        String response = interestAuthorCommandService.createInterestAuthor(request);

        assertEquals("공지영", response);
        assertThrows(AlreadyInterestedAuthorException.class, () -> {
            interestAuthorCommandService.createInterestAuthor(request);
        });
    }

    @DisplayName("관심 작가 등록시 작가가 없는 경우 예외 테스트")
    @Test
    void createInterestAuthorExceptionTest1(){
        Long memberUid = 2L;

        String authorName = "정유진";
        InterestAuthorRequest request2 = new InterestAuthorRequest(authorName, memberUid);
        assertThrows(AuthorNotFoundException.class, () -> {
            interestAuthorCommandService.createInterestAuthor(request2);
        });
    }

    @DisplayName("관심 작가 삭제하기")
    @Test
    void deleteInterestAuthorTest(){
        Long memberUid = 2L;
        String authorName = "한강";

        InterestAuthorRequest request = new InterestAuthorRequest(authorName, memberUid);
        interestAuthorCommandService.deleteInterestAuthor(request);

        assertThrows(AuthorNotFoundException.class, () -> {
            interestAuthorCommandService.deleteInterestAuthor(request);
        });
    }
}
