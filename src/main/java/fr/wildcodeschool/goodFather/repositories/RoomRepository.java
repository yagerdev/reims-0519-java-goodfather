package fr.wildcodeschool.goodFather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.wildcodeschool.goodFather.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}