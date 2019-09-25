package fr.wildcodeschool.goodFather.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private double wallA;
    private double wallB;
	private double height;
    private double lowerTotalCost;
    private double upperTotalCost;

    @ManyToOne
    private Project project;

    @ManyToOne
	private Category category;
	
	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private Set<Quantity> quantities;

    public Room() {
    }

    public Room(String name,
                double wallA, 
                double wallB, 
                double height, 
                Category category, 
                Project project
    ) {
        this.setName(name);
        this.setWallA(wallA);
        this.setWallB(wallB);
		this.setHeight(height);
        this.setLowerTotalCost(0);
        this.setUpperTotalCost(0);
		this.setCategory(category);
		this.setProject(project);
    }

    public Room(Long id, 
                String name,
                double wallA, 
                double wallB, 
                double height, 
                double lowerTotalCost, 
                double upperTotalCost, 
                Category category, 
                Project project
    ) {
        this.setId(id);
        this.setName(name);
        this.setWallA(wallA);
        this.setWallB(wallB);
		this.setHeight(height);
        this.setLowerTotalCost(lowerTotalCost);
        this.setUpperTotalCost(upperTotalCost);
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

	public Set<Quantity> getQuantities() {
		return quantities;
	}

	public void setQuantities(Set<Quantity> quantities) {
		this.quantities = quantities;
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
		this.lowerTotalCost += cost * (1 - percentRange/100);
        this.upperTotalCost += cost * (1 + percentRange/100);
	}
}