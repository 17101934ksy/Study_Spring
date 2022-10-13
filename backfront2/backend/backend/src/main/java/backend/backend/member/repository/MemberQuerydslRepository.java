package backend.backend.member.repository;

import backend.backend.member.domain.Member;
import backend.backend.member.enumerate.MemberProvider;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
