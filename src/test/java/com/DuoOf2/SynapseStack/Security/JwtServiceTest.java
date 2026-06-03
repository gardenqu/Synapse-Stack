package com.DuoOf2.SynapseStack.Security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Inject @Value fields manually without needing Spring context
        ReflectionTestUtils.setField(jwtService, "secret", "thisisaverylongsecretkeyforjwttesting12345678");
        ReflectionTestUtils.setField(jwtService, "expiration", 86400000L); // 1 day in ms
    }

    @Test
    void generateToken_shouldReturnValidToken() {
        // TODO: fill in once AppUser entity is ready
        // AppUser user = new AppUser();
        // user.setId("test-user-id");
        // user.setRoles(Set.of(new Role("USER")));
        // String token = jwtService.generateToken(user);
        // assertNotNull(token);
    }

    @Test
    void extractUserId_shouldReturnCorrectId() {
        // TODO: same — needs AppUser
    }

    @Test
    void isTokenValid_withValidToken_shouldReturnTrue() {
        // TODO: needs AppUser to generate a token first
    }

    @Test
    void isTokenValid_withGarbageToken_shouldReturnFalse() {
        // This one you CAN run right now — no AppUser needed!
        boolean result = jwtService.isTokenValid("this.is.garbage");
        assertFalse(result);
    }

    @Test
    void isTokenValid_withExpiredToken_shouldReturnFalse() {
        // You can also run this one now
        JwtService expiredService = new JwtService();
        ReflectionTestUtils.setField(expiredService, "secret", "thisisaverylongsecretkeyforjwttesting12345678");
        ReflectionTestUtils.setField(expiredService, "expiration", -1000L); // already expired
        // TODO: needs AppUser to generate the expired token
    }
}
