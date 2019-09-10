package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}