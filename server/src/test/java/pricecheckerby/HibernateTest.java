import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import pricecheckerby.model.Domain;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@DataJpaTest
@Import(TestEntityManager.class)
public class HibernateTest {
    @Autowired
    private TestEntityManager em;
    @Test
    public void someTest(){
        Transaction transaction = null;
        SessionFactory sessionFactory = em.getEntityManager().unwrap(SessionFactory.class);
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Domain domain = new Domain();
            domain.setName("lol");
            domain.setUrl("kek");
            session.save(domain);
            transaction.commit();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Domain> query = criteriaBuilder.createQuery(Domain.class);
            Root<Domain> root = query.from(Domain.class);
            query = query.select(root).where(criteriaBuilder.equal(root.get("name"), "lol"));
            System.out.println(session.createQuery(query).getResultList());
        } catch (Exception e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        }
    }
}
