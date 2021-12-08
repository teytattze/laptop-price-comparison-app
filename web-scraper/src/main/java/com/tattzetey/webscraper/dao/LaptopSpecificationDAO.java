package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.model.LaptopSpecification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * This laptop specification data access object (DAO)
 * */
@Component
public class LaptopSpecificationDAO extends BaseDAO {

    @Autowired
    LaptopSpecificationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * This function is used to save laptop and specification combination
     * if the database does not have the particular laptop and specification combination
     * @param laptopSpecification laptopSpecification object
     * @return latest laptopSpecification object
     * */
    public LaptopSpecification saveLaptopSpecification(LaptopSpecification laptopSpecification) {
        LaptopSpecification currLaptopSpecification = findLaptopSpecificationByUniqueCols(
                laptopSpecification.getLaptop().getId(),
                laptopSpecification.getSpecification().getId()
        );
        if (currLaptopSpecification == null) {
            createLaptopSpecification(laptopSpecification);
            return laptopSpecification;
        }
        return currLaptopSpecification;
    }

    /**
     * This function is used to find laptop and specification combination
     * based on the laptop's id and specification's id
     * @param laptopId laptop's id
     * @param specificationId specification's id
     * @return laptopSpecification object
     * */
    public LaptopSpecification findLaptopSpecificationByUniqueCols(String laptopId, String specificationId) {
        Session session = getSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery(
                            "from LaptopSpecification where (laptop.id = :laptopId and specification.id = :specificationId)")
                    .setParameter("laptopId", laptopId)
                    .setParameter("specificationId", specificationId);

            return (LaptopSpecification) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * This function is used to
     * save laptop and specification
     * combination into database
     * @param laptopSpecification laptopSpecification object
     * */
    private void createLaptopSpecification(LaptopSpecification laptopSpecification) {
        Session session = getSession();

        session.beginTransaction();
        session.save(laptopSpecification);
        session.getTransaction().commit();

        session.close();
    }
}
