package fr.wildcodeschool.goodFather.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    public Work() {
    }

    public Work(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Work(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "work")
    private Set<Task> tasks;

}