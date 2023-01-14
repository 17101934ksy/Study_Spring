package study.querydsl.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.repository.MemberJpaRepository;
import study.querydsl.repository.MemberRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/api/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCond cond) {
        return memberJpaRepository.search(cond);
    }

    @GetMapping("/api/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCond cond, Pageable pageable) {
        return memberRepository.searchPageComplex(cond, pageable);
    }

}
