package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.model.Source;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * This source data access object (DAO)
 * */
@Component
public class SourceDAO extends BaseDAO {

    @Autowired
    SourceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * This function is used to save source
     * if the database does not have the particular source,
     * else update latest source to database
     * @param source source object
     * @return latest source object
     * */
    public Source saveSource(Source source) {
        Source currSource = findSourceByUrl(source.getUrl());
        if (currSource == null) {
            createSource(source);
            return source;
        }
        currSource.setPrice(source.getPrice());
        updateSource(currSource);
        return currSource;
    }

    /**
     * This function is used to find source
     * based on the source's url
     * @param url source's url
     * @return source object
     * */
    public Source findSourceByUrl(String url) {
        Session session = getSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("from Source where url = :url").setParameter("url", url);
            Source source = (Source) query.getSingleResult();
            return source;
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * This function is used to
     * save source into database
     * @param source source object
     * */
    private void createSource(Source source) {
        Session session = getSession();

        session.beginTransaction();
        session.save(source);
        session.getTransaction().commit();

        session.close();
    }

    /**
     * This function is used to
     * update source into database
     * @param source source object
     * */
    private void updateSource(Source source) {
        Session session = getSession();

        session.beginTransaction();
        session.update(source);
        session.getTransaction().commit();

        session.close();
    }
}
