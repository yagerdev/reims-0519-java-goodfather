package fr.wildcodeschool.goodFather.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 30)
    private String name;

    private String address;

    @Column(length = 45)
    private String city;

    @Column(name="postal_code", length = 5)
    private String postalCode;

    @Temporal(TemporalType.DATE)
    @Column(name="creation_date")
    private Date creationDate;

    public Project() { }

    public Project(Long id, String name, String address, String city, String postalCode, Date createDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.creationDate = createDate;
    }

    public Project(String name, String address, String city, String postalCode) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Date getCreateDate() {
        return creationDate;
    }

    public void setCreateDate(Date createDate) {
        this.creationDate = createDate;
    }
    
}