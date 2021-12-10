package com.tattzetey.webscraper.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * This class is a model for source entity
 * */
@Entity
@Table(name = "sources")
public class Source {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column()
    private String id;

    @Column()
    private String website;

    @Column()
    private String url;

    @Column()
    private double price;

    @ManyToOne(targetEntity = LaptopSpecification.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "laptop_specification_id")
    private LaptopSpecification laptopSpecification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LaptopSpecification getLaptopSpecification() {
        return laptopSpecification;
    }

    public void setLaptopSpecification(LaptopSpecification laptopSpecification) {
        this.laptopSpecification = laptopSpecification;
    }

    /**
     * This function is used to validate
     * source object fields
     * */
    public Boolean validate() {
        return validateWebsite() && validateUrl() && validatePrice();
    }

    /**
     * This function is used to
     * validate website field
     * */
    public Boolean validateWebsite() {
        return !website.isEmpty();
    }

    /**
     * This function is used to
     * validate url field
     * */
    public Boolean validateUrl() {
        return !url.isEmpty();
    }

    /**
     * This function is used to
     * validate price field
     * */
    public Boolean validatePrice() {
        return price != 0;
    }

}
