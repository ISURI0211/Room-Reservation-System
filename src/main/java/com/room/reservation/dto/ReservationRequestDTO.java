package com.room.reservation.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ReservationRequestDTO {

    @NotBlank(message = "Reservation number is required")
    @Size(min = 3, max = 30, message = "Reservation number must be 3-30 characters")
    private String reservationNumber;

    @NotBlank(message = "Guest name is required")
    @Size(min = 3, max = 100, message = "Guest name must be 3-100 characters")
    private String guestName;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 255, message = "Address must be 5-255 characters")
    private String address;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{9,12}$", message = "Contact number must be 9-12 digits")
    private String contactNumber;

    @NotBlank(message = "Room type is required")
    private String roomType; // SINGLE, DOUBLE, SUITE

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
}
