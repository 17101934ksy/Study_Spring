package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberOldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
// final이 있는 필드만 생성자를 만들어준다
// AllArgsConstructor은 생성자하나인 경우에 생성자를 생성해준다.

public class MemberService {

    private final MemberOldRepository memberOldRepository;
//
//    // @Autowired 생성자가 하나일 경우에는 Autowired 생략해도 된다.
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    /**
     *회원 가입
     */
    @Transactional(readOnly = false)
    public Long join(Member member){
        validateDuplicateMember(member);
        memberOldRepository.save(member);
        return member.getId();
    }


    // member의 name에 유니크 제약조건을 달아주면 더 안전하다
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberOldRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    //회원 전체 조회
    public List<Member> findMembers() {
        return memberOldRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberOldRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        log.info("member update test1");
        Member member = memberOldRepository.findOne(id);

        log.info("member updateName");
        member.updateName(name);

        log.info("member updateName Clear");
    }
}
