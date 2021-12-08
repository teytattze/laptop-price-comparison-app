package com.tattzetey.webscraper.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * This class is a model for brand entity
 * */
@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column()
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = Laptop.class, fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "brand")
    private List<Laptop> laptops;

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

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = laptops;
    }

    /**
     * This function is used to validate
     * brand object fields
     * */
    public Boolean validate() {
        return validateName();
    }

    /**
     * This function is used to
     * validate name field
     * */
    public Boolean validateName() {
        return !name.isEmpty();
    }
}
