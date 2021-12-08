package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.config.AppConfig;
import com.tattzetey.webscraper.model.Laptop;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaptopDAOTests {

    /*
     * Get session factory bean from spring,
     * then initializes laptop dao object.
     * */
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
    private final LaptopDAO laptopDAO = new LaptopDAO(sessionFactory);

    @Test
    void itShouldSaveLaptop() {

        // Given
        String name = "Laptop Name";
        String imageUrl = "imageUrl";
        String screenSize = "screenSize";
        Laptop laptop = new Laptop();
        laptop.setName(name);
        laptop.setImageUrl(imageUrl);
        laptop.setScreenSize(screenSize);

        // When
        laptopDAO.saveLaptop(laptop);

        // Then
        Laptop currLaptop = laptopDAO.findLaptopByUniqueCols(laptop.getName(), laptop.getScreenSize());
        assertEquals(name, currLaptop.getName());
        assertEquals(imageUrl, currLaptop.getImageUrl());
        assertEquals(screenSize, currLaptop.getScreenSize());

    }

}
