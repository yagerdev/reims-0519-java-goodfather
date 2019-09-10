package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /* select CONCAT(work.name, " ", material.name) from work join task on task.work_id=work.id join material on task.material_id=material.id; */

}