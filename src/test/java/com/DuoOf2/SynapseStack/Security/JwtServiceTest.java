package com.DuoOf2.SynapseStack.Security;

import com.DuoOf2.SynapseStack.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;
    private User testUser;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", "thisisaverylongsecretkeyforjwttesting1234567890");
        ReflectionTestUtils.setField(jwtService, "expiration", 86400000L);

        testUser = new User("John", "Doe", "john@test.com", "hashedpassword");
        ReflectionTestUtils.setField(testUser, "userId", "test-user-123");
    }

    @Test
    void generateToken_shouldReturnNonNullToken() {
        String token = jwtService.generateToken(testUser);
        assertNotNull(token);
    }

    @Test
    void generateToken_shouldContainThreeParts() {
        // JWT format is always header.payload.signature
        String token = jwtService.generateToken(testUser);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void extractUserId_shouldReturnCorrectId() {
        String token = jwtService.generateToken(testUser);
        String extractedId = jwtService.extractUserId(token);
        assertEquals("test-user-123", extractedId);
    }

    @Test
    void isTokenValid_withValidToken_shouldReturnTrue() {
        String token = jwtService.generateToken(testUser);
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void isTokenValid_withGarbageToken_shouldReturnFalse() {
        assertFalse(jwtService.isTokenValid("this.is.garbage"));
    }

    @Test
    void isTokenValid_withExpiredToken_shouldReturnFalse() {
        JwtService expiredService = new JwtService();
        ReflectionTestUtils.setField(expiredService, "secret", "thisisaverylongsecretkeyforjwttesting1234567890");
        ReflectionTestUtils.setField(expiredService, "expiration", -1000L); // already expired

        String expiredToken = expiredService.generateToken(testUser);
        assertFalse(expiredService.isTokenValid(expiredToken));
    }

    @Test
    void isTokenValid_withTamperedToken_shouldReturnFalse() {
        String token = jwtService.generateToken(testUser);
        String tamperedToken = token.substring(0, token.length() - 5) + "XXXXX";
        assertFalse(jwtService.isTokenValid(tamperedToken));
    }
}