package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);
            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(teamA);
            member1.setAge(0);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(teamA);
            member2.setAge(0);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("관리자3");
            member3.setTeam(teamB);
            member3.setAge(0);
            em.persist(member3);

            // flush
            int resultCount = em.createQuery("update Member m set m.age = 20").executeUpdate();

            em.clear(); // 벌크 연산 후에는 전에 가져왔던 data 사용 안하는게 좋음

            Member findMember = em.find(Member.class, member1.getId());

            System.out.println("findMember = " + findMember);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            System.out.println("e = " + e);
        } finally {
            em.close();
        }

        emf.close();

    }
}

