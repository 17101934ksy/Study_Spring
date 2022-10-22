package jpabook.jpashop.api;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.service.MemberService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
//    private final Member2Repository member2Repository;

    //엔티티를 넣게되면 -> 멤버에 대한 모든 정보를 노출하는 단점 근데 엔티티에 @JsonIgnore 을 적용하면
    //order를 제공하지 않지만, Memeber의 order에 대한 API를 사용할 수 없다.
    // 엔티티에 presentation 스펙이 적용되면 안된다.
    // 또한 엔티티를 변경하면, Entity 스펙이 바뀌어버린다.

    @GetMapping("/api/v1/member2-find")
    public MemberOneDto memberOne(@RequestParam("id") Long id) {
        Member2 member2 = member2Repository.findOne(id);
        MemberOneDto memberDto = new MemberOneDto(member2.getId(), member2.getOrder2().getId(), member2.getName(), member2.getOrder2().getName());

        return memberDto;
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }


    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id =  memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {


        Member member = Member.builder()
                .name(request.getName())
                .build();
        Long id = memberService.join(member);
        Member findMember = memberService.findOne(id);

        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody UpdateMemberRequest updateMemberRequest) {

        memberService.update(id, updateMemberRequest.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> members = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(members.size(), members);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }


    @Getter
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private class MemberOneDto {

        private Long member2Id;
        private Long order2Id;
        private String member2Name;
        private String order2Name;

    }
}
