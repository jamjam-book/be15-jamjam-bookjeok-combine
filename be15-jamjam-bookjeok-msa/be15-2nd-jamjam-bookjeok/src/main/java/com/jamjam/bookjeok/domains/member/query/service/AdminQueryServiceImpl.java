package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.common.dto.Pagination;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import com.jamjam.bookjeok.domains.member.command.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberListResponse;
import com.jamjam.bookjeok.domains.member.query.mapper.AdminMapper;
import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import com.jamjam.bookjeok.exception.member.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminQueryServiceImpl implements AdminQueryService {

    private final AdminMapper adminMapper;

    // 멤버의 전체 목록을 조회하는 기능
    @Override
    @Transactional(readOnly = true)
    public MemberListResponse getAllMembers(PageRequest pageRequest) {
        List<MemberDTO> members = adminMapper.findAllMember(pageRequest);

        // 페이징 처리를 위해 전체 멤버의 수 조회하기
        long totalItems = adminMapper.countMembers();

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();

        return MemberListResponse.builder()
                .memberList(members)
                .pagination(Pagination.builder()
                    .currentPage(page)
                    .totalPage((int)Math.ceil((double)totalItems/size))
                    .totalItems(totalItems)
                    .build())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDetailResponse getMemberByIdOrNickname(MemberSearchRequest memberSearchRequest){
        MemberDTO member = Optional.ofNullable(
                adminMapper.findMemberByIdOrNickname(memberSearchRequest)
        ).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_EXIST_MEMBER));


        if ((memberSearchRequest.getMemberId() == null || memberSearchRequest.getMemberId().isBlank()) &&
                (memberSearchRequest.getNickname() == null || memberSearchRequest.getNickname().isBlank())) {
            throw new MemberException(MemberErrorCode.MEMBER_SEARCH_CONDITION_MISSING);
        }

        return MemberDetailResponse.builder()
                .member(member)
                .build();
    }
}
