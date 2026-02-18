package com.room.reservation.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenStore {

    // token -> session data
    private final Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();

    // Create token for a username
    public String createToken(String username, String role) {
        String token = UUID.randomUUID().toString();
        sessions.put(token, new SessionInfo(username, role, LocalDateTime.now()));
        return token;
    }

    public SessionInfo getSession(String token) {
        return sessions.get(token);
    }

    public void removeToken(String token) {
        sessions.remove(token);
    }

    public static class SessionInfo {
        private final String username;
        private final String role;
        private final LocalDateTime createdAt;

        public SessionInfo(String username, String role, LocalDateTime createdAt) {
            this.username = username;
            this.role = role;
            this.createdAt = createdAt;
        }

        public String getUsername() { return username; }
        public String getRole() { return role; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }
}
