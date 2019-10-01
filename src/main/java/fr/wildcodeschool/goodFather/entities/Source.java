package fr.wildcodeschool.goodFather.entities;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;;

@Entity
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "source", cascade = CascadeType.REMOVE)
    private Set<Task> task = new TreeSet<Task>();

    public Source() {
    }
    
    public Source(Long id, String name, User user, Set<Task> task) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.task = task;
    }

    public Source(String name, User user, Set<Task> task) {
        this.name = name;
        this.user = user;
        this.task = task;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Task> getTask() {
        return task;
    }

    public void setTask(Set<Task> task) {
        this.task = task;
    }
}