package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.config.AppConfig;
import com.tattzetey.webscraper.model.Source;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SourceDAOTests {

    /*
    * Get session factory bean from spring,
    * then initializes source dao object.
    * */
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
    private final SourceDAO sourceDAO = new SourceDAO(sessionFactory);

    @Test
    void itShouldSaveSource() {

        // Given
        String website = "Currys";
        String url = "url";
        Double price = 1000.00;
        Source source = new Source();
        source.setWebsite(website);
        source.setUrl(url);
        source.setPrice(price);

        // When
        sourceDAO.saveSource(source);

        // Then
        Source currSource = sourceDAO.findSourceByUrl(url);
        assertEquals(website, currSource.getWebsite());
        assertEquals(url, currSource.getUrl());
        assertEquals(price, currSource.getPrice());

    }

    @Test
    void itShouldUpdateSource() {

        // Given
        String website = "Currys";
        String url = "url";
        Double price = 1000.00;
        Source source = new Source();
        source.setWebsite(website);
        source.setUrl(url);
        source.setPrice(price);
        sourceDAO.saveSource(source);

        Source currSource = sourceDAO.findSourceByUrl(url);
        assertEquals(website, currSource.getWebsite());
        assertEquals(url, currSource.getUrl());
        assertEquals(price, currSource.getPrice());

        Double updatedPrice = 1200.00;
        Source updatedSource = new Source();
        updatedSource.setWebsite(website);
        updatedSource.setUrl(url);
        updatedSource.setPrice(updatedPrice);

        // When
        sourceDAO.saveSource(updatedSource);

        // Then
        Source updatedCurrSource = sourceDAO.findSourceByUrl(url);
        assertEquals(website, updatedCurrSource.getWebsite());
        assertEquals(url, updatedCurrSource.getUrl());
        assertEquals(updatedPrice, updatedCurrSource.getPrice());

    }

}
