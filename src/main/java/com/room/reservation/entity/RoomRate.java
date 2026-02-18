package com.room.reservation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "room_rates")
public class RoomRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String roomType; // SINGLE, DOUBLE, SUITE

    @Column(nullable = false)
    private double pricePerNight;

    public RoomRate() {}

    public RoomRate(String roomType, double pricePerNight) {
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    public Long getId() { return id; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }
}
