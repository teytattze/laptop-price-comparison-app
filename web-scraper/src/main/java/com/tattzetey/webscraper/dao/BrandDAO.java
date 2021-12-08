package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.model.Brand;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * This brand data access object (DAO)
 * */
@Component
public class BrandDAO extends BaseDAO {

    @Autowired
    BrandDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * This function is used to save brand
     * if the database does not have the particular brand
     * @param brand brand object
     * @return latest brand object
     * */
    public Brand saveBrand(Brand brand) {
        Brand currBrand = findBrandByName(brand.getName());
        if (currBrand == null) {
            createBrand(brand);
            return brand;
        }
        return currBrand;
    }

    /**
     * This function is used to find brand
     * based on the brand's name
     * @param name brand name
     * @return brand object
     * */
    public Brand findBrandByName(String name) {
        Session session = getSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("from Brand where name = :name").setParameter("name", name);
            Brand brand = (Brand) query.getSingleResult();
            return brand;
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * This function is used to
     * save brand into database
     * @param brand brand object
     * */
    private void createBrand(Brand brand) {
        Session session = getSession();

        session.beginTransaction();
        session.save(brand);
        session.getTransaction().commit();

        session.close();
    }
}
