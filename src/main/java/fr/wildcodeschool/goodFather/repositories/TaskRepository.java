package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.Task;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByUserId(Long userId);
    List<Task> findTasksByWorkIdAndMaterialIdAndTypologyId(Long workId, Long materialId, Long typologyId);
    Task findTaskByWorkIdAndMaterialIdAndTypologyIdAndUserId(Long workId, Long materialId, Long typologyId, Long userId);
    Task deleteById(Task taskId);
}