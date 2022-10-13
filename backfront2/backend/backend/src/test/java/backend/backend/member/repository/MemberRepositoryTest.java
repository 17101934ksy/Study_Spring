package backend.backend.member.repository;

import backend.backend.auth.enumerate.RoleType;
import backend.backend.member.domain.Member;
import backend.backend.member.enumerate.MemberProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static backend.backend.auth.enumerate.RoleType.USER;
import static backend.backend.member.enumerate.MemberProvider.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원등록_성공() throws Exception {
        //given
        Member member = Member.builder()
                .socialId("ksy123")
                .name("keyun")
                .email("ksy@ksy.com")
                .memberProvider(KAKAO)
                .roleType(USER)
                .build();

        //when
        Member savedMember = memberRepository.saveAndFlush(member);

        //then
        assertThat(savedMember.getId()).isEqualTo(member.getId());
    }
    
    @Test
    public void 회원찾기_by_소셜아이디_예외() throws Exception {
        //given
        Member member = Member.builder()
                .socialId("ksy123")
                .name("keyun")
                .email("ksy@ksy.com")
                .memberProvider(KAKAO)
                .roleType(USER)
                .build();

        //when
        Optional<Member> nullOrMember = memberRepository.findBySocialId("social123");

        //then
        Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    nullOrMember.orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getId();
                });
        assertEquals(exception.getMessage(), "404 NOT_FOUND");
    }

    @Test
    public void 회원찾기_by_소셜아이디_성공() throws Exception {
        //given
        Member member = Member.builder()
                .socialId("ksy123")
                .name("keyun")
                .email("ksy@ksy.com")
                .memberProvider(KAKAO)
                .roleType(USER)
                .build();

        //when
        memberRepository.saveAndFlush(member);
        Optional<Member> nullOrMember = memberRepository.findBySocialId("ksy123");
        Long memberId = nullOrMember.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getId();

        //then
        Assertions.assertThat(memberId).isNotNull();
    }

}