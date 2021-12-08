package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.config.AppConfig;
import com.tattzetey.webscraper.model.Specification;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecificationDAOTests {

    /*
     * Get session factory bean from spring,
     * then initializes specification dao object.
     * */
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
    private final SpecificationDAO specificationDAO = new SpecificationDAO(sessionFactory);

    @Test
    void itShouldSaveSpecification() {

        // Given
        String processor = "Intel";
        String graphicsCard = "Nvidia";
        String ram = "16 GB";
        String ssd = "256 GB";
        String hdd = "1 TB";
        Specification specification = new Specification();
        specification.setProcessor(processor);
        specification.setGraphicsCard(graphicsCard);
        specification.setRam(ram);
        specification.setSsd(ssd);
        specification.setHdd(hdd);

        // When
        specificationDAO.saveSpecification(specification);

        // Then
        Specification currSpecification = specificationDAO.findSpecificationByUniqueCols(processor, graphicsCard);
        assertEquals(processor, currSpecification.getProcessor());
        assertEquals(graphicsCard, currSpecification.getGraphicsCard());
        assertEquals(ram, currSpecification.getRam());
        assertEquals(ssd, currSpecification.getSsd());
        assertEquals(hdd, currSpecification.getHdd());

    }

}
