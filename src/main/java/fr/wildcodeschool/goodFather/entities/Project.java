package fr.wildcodeschool.goodFather.entities;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Project implements Comparable<Project> {

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

    @Column(length = 300)
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    private double lowerTotalCost;
    private double upperTotalCost;

    @ManyToOne
    private User user;

    private Long sourceId;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Set<Room> rooms = new TreeSet<Room>();

    public Project() {}

    public Project(
        Long id, 
        String name, 
        String address, 
        String city, 
        String postalCode, 
        String comment,
        Date creationDate, 
        double lowerTotalCost, 
        double upperTotalCost, 
        User user,
        Long sourceId
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.comment = comment;
        this.creationDate = creationDate;
        this.lowerTotalCost = lowerTotalCost;
        this.upperTotalCost = upperTotalCost;
        this.user = user;
        this.sourceId = sourceId;
    }

    public Project(
        String name, 
        String address, 
        String city, 
        String postalCode, 
        String comment,
        User user,
        Long sourceId
    ) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.comment = (comment == null) ? "": comment;
        this.lowerTotalCost = 0;
        this.upperTotalCost = 0;
        this.user = user;
        this.sourceId = sourceId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public double getLowerTotalCost() {
        return lowerTotalCost;
    }

    public void setLowerTotalCost(double lowerTotalCost) {
        this.lowerTotalCost = lowerTotalCost;
    }

    public double getUpperTotalCost() {
        return upperTotalCost;
    }

    public void setUpperTotalCost(double upperTotalCost) {
        this.upperTotalCost = upperTotalCost;
    }

    public void addCost(double cost, double percentRange) {
        double lowerCost = cost * (1 - percentRange/100);
        double upperCost = cost * (1 + percentRange/100);
        this.lowerTotalCost += ( (double)Math.round(lowerCost * 100) ) / 100;
        this.upperTotalCost += ( (double)Math.round(upperCost * 100) ) / 100;
    }

    public void reduceCost(double cost, double percentRange) {
        double lowerCost = cost * (1 - percentRange/100);
        double upperCost = cost * (1 + percentRange/100);
        this.lowerTotalCost -= ( (double)Math.round(lowerCost * 100) ) / 100;
        this.upperTotalCost -= ( (double)Math.round(upperCost * 100) ) / 100;
    }

    public void removeRoomCost(Room room) {
        this.lowerTotalCost -= room.getLowerTotalCost();
        this.upperTotalCost -= room.getUpperTotalCost();
    }

    @Override
    public int compareTo(Project project) {
        return project.getCreationDate().compareTo(this.creationDate);
    }

    public boolean containAtLeastOneOf(Category category) {
        for (Room room : rooms) {
            if (room.getCategory() == category) {
                return true;
            }
        }
        return false;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public long getTotalCostPerCategory(Category category) {
        double total = 0;
        for (Room room : this.rooms) {
            if (room.getCategory().equals(category)) {
                total += (room.getLowerTotalCost() + room.getUpperTotalCost()) / 2;
            }
        }
        return Math.round(total);
    }
}