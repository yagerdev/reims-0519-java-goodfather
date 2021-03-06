package fr.wildcodeschool.goodFather.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Quantity implements Comparable<Quantity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantity;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Task task;
    
    public Quantity() {}

    public Quantity(Room room, Task task, double quantity) {
        this.room = room;
        this.task = task;
        this.quantity = quantity;
    }

    public Quantity(Long id, Room room, Task task, double quantity) {
        this.id = id;
        this.room = room;
        this.task = task;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public int compareTo(Quantity quantity) {
        return this.task.compareTo(quantity.getTask());
    }
 }