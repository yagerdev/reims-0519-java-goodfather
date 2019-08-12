package fr.wildcodeschool.goodFather.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double wallA;

    private double wallB;

    private double height;

    @OneToMany
    private Category category;

    public Room() {

    }

    public Room(double wallA, double wallB, double height, Category category) {

        this.setWallA(wallA);
        this.setWallB(wallB);
        this.setHeight(height);
        this.setCategory(category);

    }

    public Room(Long id, double wallA, double wallB, double height, Category category) {

        this.setId(id);
        this.setWallA(wallA);
        this.setWallB(wallB);
        this.setHeight(height);
        this.setCategory(category);

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

}