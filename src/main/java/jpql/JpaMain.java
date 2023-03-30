package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");

            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setTeam(team);
            member.setMemberType(MemberType.USER);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', TRUE from Member m left join Team t on m.username = t.name " +
                    "where m.memberType = :memberType";
            List<Object[]> resultList = em.createQuery(query).setParameter("memberType", MemberType.USER).getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[0] = " + objects[1]);
                System.out.println("objects[0] = " + objects[2]);
            }

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

