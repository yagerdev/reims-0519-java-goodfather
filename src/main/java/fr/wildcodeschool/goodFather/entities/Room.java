package fr.wildcodeschool.goodFather.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double wallA;
    private double wallB;
    private double height;
    private String name;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Category category;

    public Room() {
    }

    public Room(double wallA, double wallB, double height, Category category, Project project) {
        this.setWallA(wallA);
        this.setWallB(wallB);
        this.setHeight(height);
        this.setCategory(category);
        this.setProject(project);
    }

    public Room(Long id, double wallA, double wallB, double height, Category category, Project project) {
        this.setId(id);
        this.setWallA(wallA);
        this.setWallB(wallB);
        this.setHeight(height);
        this.setCategory(category);
        this.setProject(project);
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}