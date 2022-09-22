package org.example;

import org.example.domain.Member;
import org.example.domain.DTO.MemberDTO;
import org.example.domain.Team;
import org.example.domain.embed.Address;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello5");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
//            Team team1 = new Team();
//            team1.setName("ttt");
//
//            Member member1 = new Member();
//            member1.setName("hello");
//            member1.setAge(12);
//            member1.setTeam(team1);
//
//            Member member2 = new Member();
//            member2.setName("hello2");
//            member2.setTeam(team1);
//
//
//            em.persist(member1);
//            em.persist(member2);
//            em.persist(team1);
//            em.flush();

//            for (int i = 0; i < 100; i++){
//                Member member = new Member();
//                member.setName("hello" + i);
//                member.setAge(i);
//                member.setTeam(team1);
//                em.persist(member);
//                em.flush();
//            }
//
//
//            List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();
//            for (Member findMember : findMembers) {
//                System.out.println("m.id = " + findMember.getId());
//            }
//            Member findResult = em.createQuery("select m from Member m where m.name =: username", Member.class)
//                    .setParameter("username", member1.getName())
//                    .getSingleResult();
//
//            Member findResult2 = em.createQuery("select m from Member m where m.name =: username", Member.class)
//                            .setParameter("username", member1.getName())
//                                    .getSingleResult();
//
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();
//
//            List<Address> resultList = em.createQuery("select o.homeAddress from Orders o", Address.class)
//                    .getResultList();
//
//            List resultList2 = em.createQuery("select m.name, m.age from Member m")
//                            .getResultList();
//
//            for (Object o : resultList2) {
//                Object[] r = (Object[]) o;
//                System.out.println("result = " + r[0]);
//                System.out.println("result = " + r[1]);
//            }
//
//            System.out.println("==================");
//            for (Address address : resultList) {
//                System.out.println(address.fullAddress());
//            }
//            System.out.println("==================");
//
//            System.out.println(findResult.getName());
//
//
//            List<Object[]> result3 = em.createQuery("select m.name, m.age from Member m")
//                            .getResultList();
//            for (Object[] objects : result3) {
//                System.out.println("result = " + objects[0]);
//                System.out.println("result = " + objects[1]);
//
//            }
//
//            List<MemberDTO> resultList1 = em.createQuery("select new org.example.domain.DTO.MemberDTO(m.name, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//
//            System.out.println("==============DTO=============");
//            for (MemberDTO memberDTO : resultList1) {
//                System.out.println("result = " + memberDTO.getName());
//                System.out.println("result = " + memberDTO.getAge());
//            }
//            System.out.println("==============DTO=============");
//
//            List<Member> resultList3 = em.createQuery("select m from Member m order by m.age asc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member : resultList3) {
//                System.out.println("member.age" + member.getAge());
//            }
//
//            List<Member> result4 = em.createQuery("select m from Member m left join m.team t on t.name =:username",
//                    Member.class)
//                    .setParameter("username", member1.getName())
//                    .getResultList();
//
//            System.out.println("========================join=====================");
//            for (Member member : result4) {
//                System.out.println("m.id = " + member.getId());
//            }
//            System.out.println("========================join=====================");
//
//            String query =
//                        "select " +
//                                    "case when m.age <= 10 then '학생요금' " +
//                                    "     when m.age >= 60 then '경로요금' " +
//                                    "     else '일반요금'" +
//                                    "from Member m";
//            List<String> resultList4 = em.createQuery(query).getResultList();
//            for (String s : resultList4) {
//                System.out.println();
//            }
//
//            String query1 = "select 'a' || 'b' from Member m";
//            List<String> resultList5 = em.createQuery(query1, String.class)
//                    .getResultList();
//
//            for (String s : resultList5) {
//                System.out.println("s = " + s);
//            }
//
//            String query2 = "select concat('a', 'b') from Member m";
//            List<String> resultList = em.createQuery(query2, String.class)
//                    .getResultList();
//            for (String s : resultList) {
//                System.out.println("s = " + s);
//            }
//
//            List<String> resultList1 = em.createQuery("select substring(m.name, 2, 5) from Member m", String.class)
//                    .getResultList();
//            for (String s : resultList1) {
//                System.out.println("s = " + s);
//            }

            String teamName = "";
            for(int i = 0; i< 30; i++){

                if (i % 10 == 0){ teamName = "team" + i; }
                Team t = new Team();
                t.setName(teamName);

                Member m = new Member();
                m.setName("member" + i);
                m.setAge(i);
                m.setTeam(t);
                em.persist(m);
                em.persist(t);
            }
            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();
            for (Member member : resultList) {
                System.out.println("m.name = "+ member.getName());
                System.out.println("m.id = "+ member.getId());
                System.out.println("m.teamName = "+ member.getTeam().getName());
                System.out.println("m.teamId = "+ member.getTeam().getId());
            }

//            List<String> resultList = em.createQuery("select trim(m.name) from Member m", String.class)
//                    .getResultList();
//            for (String s : resultList) {
//                System.out.println("name = " + s);
//            }

//            List<Object> resultList1 = em.createQuery("select count(t) from Team t group by t.name")
//                    .getResultList();
////
//            Object a = resultList1.get(0);
//            System.out.println(a);


//            for (Integer i : resultList1) {
//                System.out.println("size(team) = " + i);
//            }

//            String query = "select group_concat(m.name) from Member m";
//
//            List<String> result = em.createQuery(query, String.class)
//                            .getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}