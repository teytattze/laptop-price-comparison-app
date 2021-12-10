package com.tattzetey.webscraper.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * This class is a model for laptop specification entity
 * */
@Entity
@Table(name = "laptop_specification")
public class LaptopSpecification {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column()
    private String id;

    @ManyToOne(targetEntity = Laptop.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;

    @ManyToOne(targetEntity = Specification.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_id")
    private Specification specification;

    @OneToMany(targetEntity = Source.class, fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "laptopSpecification")
    private Set<Source> sources;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public Set<Source> getSources() {
        return sources;
    }

    public void setSources(Set<Source> sources) {
        this.sources = sources;
    }
}
