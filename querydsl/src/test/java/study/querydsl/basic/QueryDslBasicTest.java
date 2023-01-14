package study.querydsl.basic;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.domain.Member;
import study.querydsl.domain.QMember;
import study.querydsl.domain.Team;
import study.querydsl.dto.QQueryProjectionUserDto;
import study.querydsl.dto.QueryMemberDto;
import study.querydsl.dto.QueryProjectionUserDto;
import study.querydsl.dto.QueryUserDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.domain.QMember.member;
import static study.querydsl.domain.QTeam.team;

@SpringBootTest
@Transactional
public class QueryDslBasicTest {

    @Autowired
    EntityManager em;

    JPQLQueryFactory query = new JPAQueryFactory(em);

    @BeforeEach
    public void before() {

        query = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamA);
        Member member4 = new Member("member4", 40, teamA);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJPQL() {
        //member1를 찾기
        Member findByJPQL = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findByJPQL.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
//        query = new JPAQueryFactory(em);
        // QMember m = new QMember("m");
        // QMember m = QMember.member;

        Member findMember = query
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search() {

        Member findMember = query
                .selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void isNotNull() {

        Member findMember = query
                .selectFrom(member)
                .where(member.username.isNotNull().and(member.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
    
    @Test
    public void goeAndBetween() {
        Member findMember = query
                .selectFrom(member)
                .where(member.username.contains("member1")
                        .and(member.age.goe(10))
                        .and(member.age.between(10, 50)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void searchAndParam() {

        Member findMember = query
                .selectFrom(member)
                .where(
                        member.username.isNotNull(),
                        member.age.eq(10)
                )
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void resultFetch() {
        List<Member> member1 = query
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetch();

        Member member2 = query
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        Member member3 = query
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchFirst();

        QueryResults<Member> results = query
                .selectFrom(member)
                .fetchResults();

        List<Member> content = results.getResults();

    }

    /**
     * 1. 회원 정렬 순서
     * 2. 회언 이름 오름차순
     * 3. 단 2에서 회원 이름이 없으면 마지막에 출력
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member1", 100));
        em.persist(new Member("member2", 100));
        em.persist(new Member("member3", 100));

        List<Member> result = query
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(
                        member.age.desc(),
                        member.username.asc().nullsLast())
                .fetch();

        assertThat(result.get(0).getUsername()).isEqualTo("member1");
        assertThat(result.get(1).getUsername()).isEqualTo("member2");
        assertThat(result.get(2).getUsername()).isEqualTo("member3");
        assertThat(result.get(3).getUsername()).isNull();
    }

    @Test
    public void paging() {
        List<Member> result = query
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void pagingResult() {
        QueryResults<Member> queryResult = query
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertThat(queryResult.getTotal()).isEqualTo(4);
        assertThat(queryResult.getLimit()).isEqualTo(2);
        assertThat(queryResult.getOffset()).isEqualTo(1);
        assertThat(queryResult.getResults().size()).isEqualTo(2);
    }

    /**
     * queryDsl tuple
     *
     */
    @Test
    public void aggregation() {
        List<Tuple> result = query
                .select(
                        member.count(),
                        member.age.avg(),
                        member.age.min(),
                        member.age.max(),
                        member.age.sum())
                .from(member)
                .fetch();

        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구하라
     */
    @Test
    public void group() throws Exception {
        //given
        List<Tuple> result = query
                .select(team.teamName, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.teamName)
                .fetch();

        //when
        Tuple teamA = result.get(0);

        //then
        assertThat(teamA.get(team.teamName)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(25);
    }

    /**
     * 팀 A에 소속된 모든 회원
     */
    @Test
    public void join() throws Exception {
        //given
        List<Member> result = query
                .selectFrom(member)
                .join(member.team, team)
                .where(team.teamName.eq("teamA"))
                .fetch();

        //then
        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2", "member3", "member4");
    }

    @Test
    public void leftjoin() throws Exception {
        //given
        List<Member> result = query
                .selectFrom(member)
                .leftJoin(member.team, team)
                .where(team.teamName.eq("teamA"))
                .fetch();

        //then
        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2", "member3", "member4");
    }

    /**
     * 세타 조인
     * 회원의 이름이 팀 이름과 같은 회원 조회
     */
    @Test
    public void thetaJoin() throws Exception {
        //given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        //when
        List<Member> result = query
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.teamName))
                .fetch();

        //then
        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    /**
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL: select m, t from Member m left join m.team t on t.name = 'teamA'
     */
    @Test
    public void joinOnFiltering() throws Exception {
        //given
        List<Tuple> result = query
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.teamName.eq("teamC"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

        List<Tuple> result2 = query
                .select(member, team)
                .from(member)
                .join(member.team, team)
                .where(team.teamName.eq("teamC"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

        for (Tuple tuple : result2) {
            System.out.println("tuple = " + tuple);
        }
    }

    /**
     * 연관 관계가 없는 테이블 조인
     * @throws Exception
     */
    @Test
    public void join_on_no_relation() throws Exception {
        //given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Tuple> result = query
                .select(member, team)
                .from(member)
                .leftJoin(team)
                .on(member.username.eq(team.teamName))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo() {
        em.flush();
        em.clear();

        Member findMember = query
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("패치 조인 미적용")
                .isFalse();
    }

    @Test
    public void fetchJoinUse() {
        em.flush();
        em.clear();

        Member findMember = query
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("패치 조인 적용")
                .isTrue();
    }

    @Test
    public void fetchJoinUseDto() {
        em.flush();
        em.clear();

        List<LoadMemberDto> memberDtos = query
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchAll()
                .stream().map(LoadMemberDto::new)
                .collect(Collectors.toList());

        for (LoadMemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto.getUsername());
            System.out.println("memberDto = " + memberDto.getTeamName());
        }

    }

    /**
     * 나이가 가장 많은 회원 조회
     * @throws Exception
     */
    @Test
    public void subQuery() throws Exception {
        //given

        QMember ms = new QMember("memberSub");

        List<MemberDto> result = query
                .selectFrom(member)
                .where(member.age.eq(
                        select(ms.age.max())
                                .from(ms)
                ))
                .fetch()
                .stream().map(MemberDto::new)
                .collect(Collectors.toList());

        assertThat(result.get(0).getAge()).isEqualTo(40);
    }

    /**
     * 나이가 평균 이상인 멤버
     *
     */
    @Test
    public void subQueryGoe() throws Exception {
        //given
        QMember ms = new QMember("memberSub");

        //when
        List<MemberDto> result = query
                .selectFrom(member)
                .where(
                        member.age.goe(
                                select(ms.age.avg())
                                        .from(ms)
                        )
                )
                .fetchAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
        //then
        assertThat(result.size())
                .isEqualTo(2);
    }



    /**
     * in
     */
    /**
     * 나이가 평균 이상인 멤버
     *
     */
    @Test
    public void subQueryIn() throws Exception {
        //given
        QMember ms = new QMember("memberSub");

        //when
        List<MemberDto> result = query
                .selectFrom(member)
                .where(
                        member.age.in(
                                select(ms.age)
                                        .from(ms)
                                        .where(ms.age.gt(10))
                        )
                )
                .fetchAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
        //then
        assertThat(result.size())
                .isEqualTo(3);
    }

    /**
     * select 절에 subQuery
     */
    @Test
    public void selectSubQuery() throws Exception {
        //given
        QMember ms = new QMember("memberSub");

        //when

        List<Tuple> result = query
                .select(member.username,
                        select(ms.age.avg())
                                .from(ms))
                .from(member)
                .fetch();

        //then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

    }

    @Test
    public void basicCase() {
        List<String> result = query
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void complexCase() {
        List<String> result = query
                .select(
                        new CaseBuilder()
                                .when(member.age.between(0, 20)).then("0 ~ 20살")
                                .when(member.age.between(21, 40)).then("21 ~ 40살")
                                .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void constant() {
        List<Tuple> result = query
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    // stringValue() 효과적이다
    @Test
    public void concat() {

        // username_age
        List<String> result = query
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetch();

        for (String s : result) {
            System.out.println("result = " + result);
        }
    }
    
    @Test
    public void simpleProjection() throws Exception {
        List<String> result = query
                .select(member.username)
                .from(member)
                .fetch();
    }
    
    @Test
    public void tupleProjection() throws Exception {
        List<Tuple> result = query
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("username = " + tuple.get(member.username));
            System.out.println("age = " + tuple.get(member.age));
        }
    }

    @Test
    public void findDtoBySetter() throws Exception {
        List<QueryMemberDto> result = query
                .select(Projections.bean(QueryMemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();
    }

    @Test
    public void findDtoByField() throws Exception {
        List<QueryMemberDto> result = query
                .select(Projections.fields(QueryMemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();
    }

    @Test
    public void findUserDtoByField() throws Exception {
        List<QueryUserDto> result = query
                .select(Projections.fields(QueryUserDto.class,
                        member.username.as("name"),
                        member.age))
                .from(member)
                .fetch();
    }

    @Test
    public void findDtoByConstructor() throws Exception {
        List<QueryMemberDto> result = query
                .select(
                        Projections.constructor(
                        QueryMemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();
    }

    @Test
    public void findQueryUserDto() throws Exception {
        List<QueryUserDto> result = query
                .select(
                        Projections.constructor(
                                QueryUserDto.class,
                                member.username,
                                member.age))
                .from(member)
                .fetch();

        for (QueryUserDto queryUserDto : result) {
            System.out.println("queryUserDto = " + queryUserDto.getName());
        }
    }


    @Test
    public void findUserDtoBySubQuery() throws Exception {
        //given
        QMember ms = new QMember("memberSub");

        //when
        List<QueryUserDto> result = query
                .select(
                        Projections.fields(
                                QueryUserDto.class,
                                member.username.as("name"),
                                ExpressionUtils.as(
                                        select(ms.age.max())
                                                .from(ms), "age")))
                .from(member)
                .fetch();
        //then
        for (QueryUserDto queryUserDto : result) {
            System.out.println("queryUserDto = " + queryUserDto.getName());
        }
    }

    @Test
    public void findDtoByQueryProjection() throws Exception {
        List<QueryProjectionUserDto> result = query
                .select(
                        new QQueryProjectionUserDto(
                        member.username,
                        member.age
                ))
                .from(member)
                .fetch();

        for (QueryProjectionUserDto queryProjectionUserDto : result) {
            System.out.println("queryProjectionUserDto.getUsername() = " + queryProjectionUserDto.getUsername());
            System.out.println("queryProjectionUserDto.getAge() = " + queryProjectionUserDto.getAge());
        }
    }

    @Test
    public void dynamicQueryBooleanBuilder() throws Exception {
        //given
        String usernameParam = "member1";
        Integer ageParam = null;

        //when
        List<Member> result = searchMember1(usernameParam, ageParam);

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {

        BooleanBuilder builder = new BooleanBuilder();

        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }

        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return query
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    @Test
    public void dynamicQueryWhereParam() {
        String usernameParam = "member1";
        Integer ageParam = null;

        //when
        List<Member> result = searchMember2(usernameParam, ageParam);

    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {

        return query
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression usernameEqAndAgeEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }


    static class MemberDto {
        private String username;
        private int age;

        public MemberDto() {}
        public MemberDto(Member member) {
            this.username = member.getUsername();
            this.age = member.getAge();
        }

        public String getUsername() {
            return this.username;
        }

        public int getAge() {
            return this.age;
        }
    }


    static class LoadMemberDto {
        private String username;
        private String teamName;

        public LoadMemberDto() {}

        public LoadMemberDto(Member member) {
            this.username = member.getUsername();
            this.teamName = member.getTeam().getTeamName();
        }

        public String getUsername() {
            return this.username;
        }


        public String getTeamName() {
            return this.teamName;
        }
    }


    @Test
    public void bulkUpdate() throws Exception {

        long count = query
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        em.flush();
        em.clear();
    }

    @Test
    public void bulkAdd() throws Exception {

        long count = query
                .update(member)
                .set(member.age, member.age.add(1))
                .set(member.age, member.age.multiply(2))
                .execute();

        em.flush();
        em.clear();
    }

    @Test
    public void bulkDelete() throws Exception {
        long count = query
                .delete(member)
                .where(member.age.lt(30))
                .execute();

        em.flush();
        em.clear();
    }
    @Test
    public void sqlFunction() throws Exception {
        List<String> result = query
                .select(
                        Expressions
                                .stringTemplate("function('replace', {0}, {1}, {2})",
                                        member.username, "member", "M"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void sqlFunction2() throws Exception {
        List<String> result = query
                .select(member.username)
                .from(member)
//                .where(member.username.eq(Expressions.stringTemplate("function('lower', {0}", member.username)))
                .where(member.username.eq(member.username.lower()))
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

}
