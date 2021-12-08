package com.tattzetey.webscraper.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * This abstract class is the
 * based class for all the
 * data access object (DAO) class
 * */
public abstract class BaseDAO {

    protected final SessionFactory sessionFactory;

    BaseDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

}
