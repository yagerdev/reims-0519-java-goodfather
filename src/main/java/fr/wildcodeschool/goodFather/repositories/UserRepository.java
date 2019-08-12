package fr.wildcodeschool.goodFather.repositories;

import fr.wildcodeschool.goodFather.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}