package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}