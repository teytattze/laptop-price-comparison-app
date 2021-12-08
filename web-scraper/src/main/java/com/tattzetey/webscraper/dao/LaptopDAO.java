package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.model.Laptop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * This laptop data access object (DAO)
 * */
@Component
public class LaptopDAO extends BaseDAO {

    @Autowired
    LaptopDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * This function is used to save laptop
     * if the database does not have the particular laptop
     * @param laptop laptop object
     * @return latest laptop object
     * */
    public Laptop saveLaptop(Laptop laptop) {
        Laptop currLaptop = findLaptopByUniqueCols(laptop.getName(), laptop.getScreenSize());
        if (currLaptop == null) {
            createLaptop(laptop);
            return laptop;
        }
        return currLaptop;
    }

    /**
     * This function is used to find laptop
     * based on the laptop's name and screen size
     * @param name laptop's name
     * @param screenSize laptop's screenSize
     * @return latest laptop's
     * */
    public Laptop findLaptopByUniqueCols(String name, String screenSize) {
        Session session = getSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery(
                            "from Laptop where (name = :name and screenSize = :screenSize)")
                    .setParameter("name", name)
                    .setParameter("screenSize", screenSize);

            Laptop laptop = (Laptop) query.getSingleResult();
            return laptop;
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * This function is used to
     * save laptop into database
     * @param laptop laptop object
     * */
    private void createLaptop(Laptop laptop) {
        Session session = getSession();

        session.beginTransaction();
        session.save(laptop);
        session.getTransaction().commit();

        session.close();
    }

}
