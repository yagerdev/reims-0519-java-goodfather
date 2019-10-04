package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.Work;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
}