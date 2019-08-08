package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> { 
}