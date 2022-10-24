package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Team save(Team team) {
        em.persist(team);
        return team;
    }

    public void delete(Team team) {
        em.remove(team);
    }

    public Team find(Long id) {
        return em.find(Team.class, id);
    }

    public Optional<Team> findById(Long id) {
        return Optional.ofNullable(em.find(Team.class, id));
    }

    public List<Team> findAll() {
        return em.createQuery(
                "select t from Team t", Team.class)
                .getResultList();
    }

    public long count() {
        return em.createQuery(
                "select count(t) from Team t", Long.class)
                .getSingleResult();
    }
}
