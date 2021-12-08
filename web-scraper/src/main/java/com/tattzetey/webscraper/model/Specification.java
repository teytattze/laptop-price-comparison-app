package com.tattzetey.webscraper.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * This class is a model for specification entity
 * */
@Entity
@Table(name = "specifications", uniqueConstraints = @UniqueConstraint(columnNames = {"processor", "graphics_card"}))
public class Specification {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column()
    private String id;

    @Column()
    private String processor;

    @Column(name = "graphics_card")
    private String graphicsCard;

    @Column()
    private String ssd;

    @Column()
    private String hdd;

    @Column()
    private String ram;

    @OneToMany(targetEntity = LaptopSpecification.class, fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "specification")
    private Set<LaptopSpecification> laptopSpecifications;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Set<LaptopSpecification> getLaptopSpecifications() {
        return laptopSpecifications;
    }

    public void setLaptopSpecifications(Set<LaptopSpecification> laptopSpecifications) {
        this.laptopSpecifications = laptopSpecifications;
    }

    /**
     * This function is used to validate
     * specification object fields
     * */
    public Boolean validate() {
        return validateProcessor() && validateGraphicsCard() && validateStorage() && validateRam();
    }

    /**
     * This function is used to
     * validate processor field
     * */
    public Boolean validateProcessor() {
        return !processor.isEmpty();
    }

    /**
     * This function is used to
     * validate graphics card field
     * */
    public Boolean validateGraphicsCard() {
        return !graphicsCard.isEmpty();
    }

    /**
     * This function is used to
     * validate storage (SSD & HDD) field
     * */
    public Boolean validateStorage() {
        return !ssd.isEmpty() || !hdd.isEmpty();
    }

    /**
     * This function is used to
     * validate ram field
     * */
    public Boolean validateRam() {
        return !ram.isEmpty();
    }
}
