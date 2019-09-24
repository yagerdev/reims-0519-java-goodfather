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

    private double wallA;
    private double wallB;

	private double height;
	
	private double totalCost;
    private String name;

    @ManyToOne
    private Project project;

    @ManyToOne
	private Category category;
	
	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private Set<Quantity> quantities;

    public Room() {
    }

    public Room(double wallA, double wallB, double height, Category category, Project project) {
        this.setWallA(wallA);
        this.setWallB(wallB);
		this.setHeight(height);
		this.setTotalCost(0);
		this.setCategory(category);
		this.setProject(project);
    }

    public Room(Long id, double wallA, double wallB, double height, double totalCost, Category category, Project project) {
        this.setId(id);
        this.setWallA(wallA);
        this.setWallB(wallB);
		this.setHeight(height);
		this.setTotalCost(totalCost);
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

	public boolean addTaskQuantity(Quantity quantity) {
		return this.quantities.add(quantity);
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public void addCost(double taskCost) {
		this.totalCost += taskCost;
	}

	public Set<Quantity> getQuantities() {
		return quantities;
	}

	public void setQuantities(Set<Quantity> quantities) {
		this.quantities = quantities;
	}
}