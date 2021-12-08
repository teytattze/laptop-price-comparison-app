package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.config.AppConfig;
import com.tattzetey.webscraper.model.Brand;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrandDAOTests {

    /*
     * Get session factory bean from spring,
     * then initializes brand dao object.
     * */
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
    private final BrandDAO brandDAO = new BrandDAO(sessionFactory);

    @Test
    void itShouldSaveBrand() {

        // Given
        String brandName = "MSI";
        Brand brand = new Brand();
        brand.setName(brandName);

        // When
        brandDAO.saveBrand(brand);

        // Then
        Brand currBrand = brandDAO.findBrandByName(brandName);
        assertEquals(brandName, currBrand.getName());

    }

}
