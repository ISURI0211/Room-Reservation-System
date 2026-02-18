package com.room.reservation.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.room.reservation.entity.RoomRate;
import com.room.reservation.entity.User;
import com.room.reservation.repository.RoomRateRepository;
import com.room.reservation.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoomRateRepository roomRateRepository;

    public DataSeeder(UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      RoomRateRepository roomRateRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roomRateRepository = roomRateRepository;
    }

    @Override
    public void run(String... args) {

        // Default admin user
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", passwordEncoder.encode("admin123"), "ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Default admin created: admin / admin123");
        }

        // Default room rates
        seedRate("SINGLE", 10000);
        seedRate("DOUBLE", 15000);
        seedRate("SUITE", 25000);
    }

    private void seedRate(String roomType, double price) {
        if (!roomRateRepository.existsByRoomType(roomType)) {
            roomRateRepository.save(new RoomRate(roomType, price));
            System.out.println("✅ Room rate seeded: " + roomType + " = " + price);
        }
    }
}
