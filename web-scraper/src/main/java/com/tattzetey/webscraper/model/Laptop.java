package com.tattzetey.webscraper.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * This class is a model for laptop entity
 * */
@Entity
@Table(name = "laptops", uniqueConstraints =
@UniqueConstraint(columnNames = {"name", "screen_size"}))
public class Laptop {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column()
    private String id;

    @Column()
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "screen_size")
    private String screenSize;

    @ManyToOne(targetEntity = Brand.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(targetEntity = LaptopSpecification.class, fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "laptop")
    private Set<LaptopSpecification> laptopSpecifications;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    /**
     * This function is used to validate
     * laptop object fields
     * */
    public Boolean validate() {
        return validateName() && validateImageUrl() && validateScreenSize();
    }

    /**
     * This function is used to
     * validate name field
     * */
    public Boolean validateName() {
        return !name.isEmpty();
    }

    /**
     * This function is used to
     * validate imageUrl field
     * */
    public Boolean validateImageUrl() {
        return !imageUrl.isEmpty();
    }

    /**
     * This function is used to
     * validate screen size field
     * */
    public Boolean validateScreenSize() {
        return !screenSize.isEmpty();
    }
}
