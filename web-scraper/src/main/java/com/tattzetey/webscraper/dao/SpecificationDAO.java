package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.model.Specification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * This specification data access object (DAO)
 * */
@Component
public class SpecificationDAO extends BaseDAO {

    @Autowired
    SpecificationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * This function is used to save specification
     * if the database does not have the particular specification
     * @param specification specification object
     * @return latest specification object
     * */
    public Specification saveSpecification(Specification specification) {
        Specification currSpecification = findSpecificationByUniqueCols(
                specification.getProcessor(),
                specification.getGraphicsCard()
        );
        if (currSpecification == null) {
            createSpecification(specification);
            return specification;
        }
        return currSpecification;
    }

    /**
     * This function is used to find specification
     * based on the specification's processor and graphics card
     * @param graphicsCard specification's graphics card
     * @param processor specification's processor
     * @return specification object
     * */
    public Specification findSpecificationByUniqueCols(String processor, String graphicsCard) {
        Session session = getSession();
        session.beginTransaction();

        try {
            Query query = session.createQuery(
                            "from Specification where (processor = :processor and graphicsCard = :graphicsCard)")
                    .setParameter("processor", processor)
                    .setParameter("graphicsCard", graphicsCard);

            return (Specification) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * This function is used to
     * save specification into database
     * @param specification specification object
     * */
    private void createSpecification(Specification specification) {
        Session session = getSession();

        session.beginTransaction();
        session.save(specification);
        session.getTransaction().commit();

        session.close();
    }

}
