package kakaoToy1.kakaoToy1.domain;

import kakaoToy1.kakaoToy1.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ChattingLogTest {

    @Test
    public void 채팅로그생성() throws Exception {
        //given
        Member member = new Member();
        TestSpace testSpace = new TestSpace();
        ChattingLog chattingLog1 = new ChattingLog("안녕하세요", member, testSpace);
        ChattingLog chattingLog2 = new ChattingLog("저는 누구입니다.", member, testSpace);

        //when
        chattingLog1.changeDeleteDate();

        //then
        assertThat(chattingLog1.getContent()).isEqualTo("안녕하세요");
        assertThat(chattingLog2.getContent()).isEqualTo("저는 누구입니다.");
        assertThat(chattingLog1.getMember()).isEqualTo(member);
        assertThat(chattingLog2.getDeleteDate()).isNull();
        assertThat(chattingLog1.getDeleteDate()).isBefore(LocalDateTime.now());
        System.out.println("============================================================");
        System.out.println("chattingLog1.getCreatedDate() = " + chattingLog1.getCreatedDate());
        System.out.println("============================================================");
    }
}