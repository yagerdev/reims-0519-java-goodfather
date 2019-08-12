package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.Typology;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypologyRepository extends JpaRepository<Typology, Long> {
}