package com.DuoOf2.SynapseStack.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
public class AuthActionsTest {

    @Test
    void testConstructorAndGetters(){
        User user= new User();
        LocalDateTime expiresAt= LocalDateTime.now().plusMinutes(15);
        AuthActions authActions = new AuthActions(user,"password reset","token",expiresAt);

        assertEquals("password reset",authActions.getActionType());
        assertEquals("token",authActions.getTokenHash());
        assertEquals(user,authActions.getUser());
        assertEquals( expiresAt,authActions.getExpiresAt());
        assertNull(authActions.getUsedAt());
        assertNull(authActions.getActionId());

    }
    @Test
    void testSetters(){
        User user = new User();
        AuthActions authActions = new AuthActions();
        LocalDateTime usedAt= LocalDateTime.now();
        LocalDateTime expiresAt=LocalDateTime.now().plusMinutes(15);

        authActions.setUser(user);
        authActions.setActionType("change email");
        authActions.setExpiresAt(expiresAt);
        authActions.setTokenHash("token2");
        authActions.setUsedAt(usedAt);

        assertEquals(user,authActions.getUser());
        assertEquals("change email",authActions.getActionType());
        assertEquals(expiresAt,authActions.getExpiresAt());
        assertEquals("token2",authActions.getTokenHash());
        assertEquals(usedAt,authActions.getUsedAt());



    }


}
