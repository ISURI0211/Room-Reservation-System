package com.room.reservation.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.room.reservation.config.TokenStore;
import com.room.reservation.dto.LoginRequestDTO;
import com.room.reservation.dto.LoginResponseDTO;
import com.room.reservation.entity.User;
import com.room.reservation.exception.NotFoundException;
import com.room.reservation.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenStore tokenStore) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername().trim())
                .orElseThrow(() -> new NotFoundException("Invalid username or password"));

        boolean ok = passwordEncoder.matches(dto.getPassword(), user.getPasswordHash());
        if (!ok) {
            throw new NotFoundException("Invalid username or password");
        }

        String token = tokenStore.createToken(user.getUsername(), user.getRole());
        return new LoginResponseDTO(true, "Login successful", token, user.getRole());
    }
}
