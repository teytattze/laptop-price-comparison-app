package com.tattzetey.webscraper.dao;

import com.tattzetey.webscraper.config.AppConfig;
import com.tattzetey.webscraper.model.Laptop;
import com.tattzetey.webscraper.model.LaptopSpecification;
import com.tattzetey.webscraper.model.Specification;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaptopSpecificationDAOTests {

    /*
     * Get session factory bean from spring,
     * then initializes source dao object.
     * */
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
    private final LaptopDAO laptopDAO = new LaptopDAO(sessionFactory);
    private final SpecificationDAO specificationDAO = new SpecificationDAO(sessionFactory);
    private final LaptopSpecificationDAO laptopSpecificationDAO = new LaptopSpecificationDAO(sessionFactory);

    @Test
    void itShouldSaveLaptopSpecification() {

        // Given
        String name = "Laptop Name";
        String imageUrl = "imageUrl";
        String screenSize = "screenSize";
        Laptop laptop = new Laptop();
        laptop.setName(name);
        laptop.setImageUrl(imageUrl);
        laptop.setScreenSize(screenSize);

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

        laptopDAO.saveLaptop(laptop);
        specificationDAO.saveSpecification(specification);
        Laptop currLaptop = laptopDAO.findLaptopByUniqueCols(name, screenSize);
        Specification currSpecification = specificationDAO.findSpecificationByUniqueCols(processor, graphicsCard);

        LaptopSpecification laptopSpecification = new LaptopSpecification();
        laptopSpecification.setLaptop(laptop);
        laptopSpecification.setSpecification(specification);

        // When
        laptopSpecificationDAO.saveLaptopSpecification(laptopSpecification);

        // Then
        LaptopSpecification currLaptopSpecification = laptopSpecificationDAO.findLaptopSpecificationByUniqueCols(currLaptop.getId(), currSpecification.getId());
        assertEquals(currLaptop.getId(), currLaptopSpecification.getLaptop().getId());
        assertEquals(currSpecification.getId(), currLaptopSpecification.getSpecification().getId());

    }

}
