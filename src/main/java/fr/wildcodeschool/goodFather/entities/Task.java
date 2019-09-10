package fr.wildcodeschool.goodFather.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;

    private String unit;

    private double percentRange;

    @ManyToMany
    @JoinColumn
    private Set<Typology> typology;

    @ManyToOne
    @JoinColumn
    private Material material;

    @ManyToOne
    @JoinColumn
    private Work work;

    public Task(Long id, 
                double price, 
                String unit, 
                double percentRange, 
                Typology typology, 
                Material material, 
                Work work
    ) {
        this.setId(id);
        this.setPrice(price);
        this.setUnit(unit);
        this.setPercentRange(percentRange);
        this.setMaterial(material);
        this.setWork(work);
    }

    public Task(double price,
                String unit,
                double percentRange,
                Material material, 
                Work work
    ) {
        this.setPrice(price);
        this.setUnit(unit);
        this.setPercentRange(percentRange);
        this.setMaterial(material);
        this.setWork(work);
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

    public void setTypology(Set<Typology> typology) {
        this.typology = typology;
    }
}