package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.Material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}