package fr.wildcodeschool.goodFather.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 30)
    private String name;

    private String address;

    @Column(length = 45)
    private String city;

    @Column(length = 5)
    private String postalCode;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "project")
    private Set<Room> rooms;

    public Project() { }

    public Project(Long id, String name, String address, String city, String postalCode, Date createDate, User user) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.creationDate = createDate;
        this.user = user;
    }

    public Project(String name, String address, String city, String postalCode, Date createDate, User user) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.setCreateDate(createDate);
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
    
}