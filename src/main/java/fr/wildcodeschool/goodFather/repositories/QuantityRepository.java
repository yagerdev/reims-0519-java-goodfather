package fr.wildcodeschool.goodFather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.wildcodeschool.goodFather.entities.Quantity;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {
    public Quantity findQuantityByRoomIdAndTaskId(Long roomId, Long taskId);
}