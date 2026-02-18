package com.room.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room.reservation.entity.RoomRate;

public interface RoomRateRepository extends JpaRepository<RoomRate, Long> {
    Optional<RoomRate> findByRoomType(String roomType);
    boolean existsByRoomType(String roomType);
}
