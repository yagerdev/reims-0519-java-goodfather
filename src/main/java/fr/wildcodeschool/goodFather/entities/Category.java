package fr.wildcodeschool.goodFather.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany
    private Set<Typology> typologies = new HashSet<>();

    public Category(){

    }
    
    public Category(String name){
        this.name= name;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Typology> getTypologies() {
        return typologies;
    }

    public void setTypologies(Set<Typology> typologies) {
        this.typologies = typologies;
    }
}
