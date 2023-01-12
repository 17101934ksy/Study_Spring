package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.domain.Member;
import study.datajpa.dto.MemberDto;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

//    @GetMapping("/members")
//    public ResponseEntity list(@PageableDefault(size = 5, sort="username") Pageable pageable) {
//        return new ResponseEntity(
//                memberRepository
//                        .findAll(pageable).map(member -> new MemberDto(member.getId(),
//                                member.getUsername(), null)), HttpStatus.OK);
//    }

    @GetMapping("/members")
    public ResponseEntity list(@PageableDefault(size = 5, sort = "username") Pageable pageable) {
        return new ResponseEntity(
                memberRepository
                        .findAll(pageable).map(MemberDto::new), HttpStatus.OK);}



    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("member" + i, i));
        }
    }


}
