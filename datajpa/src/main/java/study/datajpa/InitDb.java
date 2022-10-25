//package study.datajpa;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.catalina.valves.rewrite.InternalRewriteMap;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import study.datajpa.domain.Member;
//import study.datajpa.domain.Team;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
//        public void dbInit() {
//            int randomInt;
//
//            for(char c='A'; c<'J'; c++) {
//                Team team = Team.builder().name("team"+ c).build();
//                em.persist(team);
//            }
//
//            for(int i=0; i<3000; i++) {
//                randomInt = (int) (Math.random() * 9) + 65;
//
//                Member member = new Member("member"+i, (int) (Math.random()*21) + 40, selectTeam(randomInt));
//                em.persist(member);
//            }
//        }
//
//        public Team selectTeam(int randomInt) {
//
//            switch (randomInt) {
//
//                case 65:
//                    return em.find(Team.class, 1L);
//                case 66:
//                    return em.find(Team.class, 2L);
//                case 67:
//                    return em.find(Team.class, 3L);
//                case 68:
//                    return em.find(Team.class, 4L);
//                case 69:
//                    return em.find(Team.class, 5L);
//                case 70:
//                    return em.find(Team.class, 6L);
//                case 71:
//                    return em.find(Team.class, 7L);
//                case 72:
//                    return em.find(Team.class, 8L);
//                case 73:
//                    return em.find(Team.class, 9L);
//                case 74:
//                    return em.find(Team.class, 10L);
//            }
//            return null;
//        }
//    }
//}
