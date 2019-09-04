package fr.wildcodeschool.goodFather.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ManyToAny;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double price;

    private double multiplicator;

    private double percentRange;

    @ManyToMany
    @JoinColumn
    private Typology typology;

    @OneToMany(mappedBy = "material")
    private Material material;

    @OneToMany(mappedBy = "work")
    private Work work;

    public Task(Long id, double price, double multiplicator, double percentRange, Typology typology, Material material, Work work) {
        this.setId(id);
        this.setPrice(price);
        this.setMultiplicator(multiplicator);
        this.setPercentRange(percentRange);
        this.setTypology(typology);
        this.setMaterial(material);
        this.setWork(work);
    }

    public Task(double price, double multiplicator, double percentRange, Typology typology, Material material, Work work) {
        this.setPrice(price);
        this.setMultiplicator(multiplicator);
        this.setPercentRange(percentRange);
        this.setTypology(typology);
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

    public double getMultiplicator() {
        return multiplicator;
    }

    public void setMultiplicator(double multiplicator) {
        this.multiplicator = multiplicator;
    }

    public double getPercentRange() {
        return percentRange;
    }

    public void setPercentRange(double percentRange) {
        this.percentRange = percentRange;
    }

    public Typology getTypology() {
        return typology;
    }

    public void setTypology(Typology typology) {
        this.typology = typology;
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
}