package com.DuoOf2.SynapseStack.entity;

import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
public class UserTest {
    @Test
    void testConstructorAndGetters(){
        User user = new User("John","Cooper","JohnCopper112@gmail.com","$2y$10$X7k4gyW8Pz6aGv2q19fK3bY9t5WjE/x2Yy/Ua28zZ/X242kYxK");

        assertEquals("John",user.getFirstName());
        assertEquals("Cooper",user.getLastName());
        assertEquals("JohnCopper112@gmail.com",user.getEmail());
        assertEquals("$2y$10$X7k4gyW8Pz6aGv2q19fK3bY9t5WjE/x2Yy/Ua28zZ/X242kYxK",user.getPasswordHash());

    }


    @Test
     void testSetters(){
        User user = new User();
        user.setEmail("AdamBrown112@gmail.com");
        user.setBirthDay( LocalDate.of(1999,9,20));
        user.setFirstName("Adam");
        user.setLastName("Brown");
        user.setPasswordHash("$2y$10$X7k4gyW8Pz6aGv2q19fK3bY9t5WjE/x2Yy/Ua28zZ/X242kYxK");


        assertEquals("AdamBrown112@gmail.com",user.getEmail());
        assertEquals(LocalDate.of(1999,9,20),user.getBirthDay());
        assertEquals("Adam",user.getFirstName());
        assertEquals("Brown",user.getLastName());
        assertEquals("$2y$10$X7k4gyW8Pz6aGv2q19fK3bY9t5WjE/x2Yy/Ua28zZ/X242kYxK",user.getPasswordHash());


    }


    @Test
    void testCollectionsAreInitialized() {

        User user = new User();

        assertNotNull(user.getLogs());
        assertNotNull(user.getAuthActions());
        assertNotNull(user.getRefreshTokens());
        assertNotNull(user.getUserActivities());
        assertNotNull(user.getLoginAttempts());

        assertTrue(user.getLogs().isEmpty());
        assertTrue(user.getAuthActions().isEmpty());
    }

    @Test
    void testUserIdInitiallyNull() {

        User user = new User();

        assertNull(user.getUserId());
    }

}
