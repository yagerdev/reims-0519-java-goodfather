package fr.wildcodeschool.goodFather.entities;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;

    private String unit;

    private double percentRange;

    @ManyToOne
    private Typology typology;

    @ManyToOne
    private Material material;

    @ManyToOne
    private Work work;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private Set<Quantity> quantities = new TreeSet<Quantity>();

    private Long userId;

    public Task(
        Long id, 
        double price, 
        String unit, 
        double percentRange, 
        Typology typology, 
        Material material, 
        Work work,
        Long userId
    ) {
        this.setId(id);
        this.setPrice(price);
        this.setUnit(unit);
        this.setPercentRange(percentRange);
        this.setTypology(typology);
        this.setMaterial(material);
        this.setWork(work);
        this.setUserId(userId);
    }

    public Task(
        double price,
        String unit,
        double percentRange,
        Typology typology,
        Material material, 
        Work work,
        Long userId
    ) {
        this.setPrice(price);
        this.setUnit(unit);
        this.setPercentRange(percentRange);
        this.setTypology(typology);
        this.setMaterial(material);
        this.setWork(work);
        this.setUserId(userId);
    }

    public Task() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPercentRange() {
        return percentRange;
    }

    public void setPercentRange(double percentRange) {
        this.percentRange = percentRange;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Typology getTypology() {
        return typology;
    }

    public void setTypology(Typology typology) {
        this.typology = typology;
    }

    public String constructName() {
        return this.work.getName() + " " + this.material.getName();
    }

    public Set<Quantity> getQuantities() {
        return quantities;
    }

    public void setQuantities(Set<Quantity> quantities) {
        this.quantities = quantities;
    }

    @Override
    public int compareTo(Task task) {
        return this.constructName().compareTo(task.constructName());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void deleteQuantities() {
        for (Quantity quantity : this.quantities) {
            quantity.getRoom().reduceCost(this.price * quantity.getQuantity(), this.percentRange);
            quantity.getRoom().getProject().reduceCost(this.price * quantity.getQuantity(), this.percentRange);
        }
    }

    public void update(double price, double percentRange, String unit) {
        for (Quantity quantity : this.quantities) {
            quantity.getRoom().reduceCost(this.price * quantity.getQuantity(), this.percentRange);
            quantity.getRoom().addCost(price * quantity.getQuantity(), percentRange);
            quantity.getRoom().getProject().reduceCost(this.price * quantity.getQuantity(), this.percentRange);
            quantity.getRoom().getProject().addCost(price * quantity.getQuantity(), percentRange);
        }
        this.setPrice(price);
        this.setPercentRange(percentRange);
        this.setUnit(unit);
    }
}