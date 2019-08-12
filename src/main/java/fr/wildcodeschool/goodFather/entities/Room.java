package fr.wildcodeschool.goodFather.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    double wallA;

    @Column
    double wallB;

    @Column
    double height;

    public Room() {

    }

    public Room(double wallA, double wallB, double height) {

        this.setWallA(wallA);
        this.setWallB(wallB);
        this.setHeight(height);

    }

    public Room(Long id, double wallA, double wallB, double height) {

        this.setId(id);
        this.setWallA(wallA);
        this.setWallB(wallB);
        this.setHeight(height);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWallA() {
        return wallA;
    }

    public void setWallA(double wallA) {
        this.wallA = wallA;
    }

    public double getWallB() {
        return wallB;
    }

    public void setWallB(double wallB) {
        this.wallB = wallB;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}