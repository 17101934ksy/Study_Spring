package study1.springboot1.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void registerMember(){
        Member member1 = new Member(1L, "koseyun1", Grade.VIP);
        Member member2 = new Member(1L, "koseyun2", Grade.BASIC);

        assertThat(member1.getClass()).isEqualTo(Member.class);
        assertThat(member2.getClass()).isEqualTo(Member.class);
    }
}