package com.example.Brainstorm.Models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UserTests {

    @Test
    void testUserCreationWithAllArgsConstructor() {
        User user = new User(
                1L,
                "John",
                25,
                "9876543210",
                "john@example.com",
                "john123",
                "password123",
                "USER"
        );

        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals(25, user.getAge());
        assertEquals("9876543210", user.getNumber());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("john123", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    void testUserSettersAndGetters() {
        User user = new User();

        user.setId(2L);
        user.setName("Alice");
        user.setAge(22);
        user.setNumber("9999999999");
        user.setEmail("alice@example.com");
        user.setUsername("alice22");
        user.setPassword("securepass");
        user.setRole("ADMIN");

        assertEquals(2L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals(22, user.getAge());
        assertEquals("9999999999", user.getNumber());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("alice22", user.getUsername());
        assertEquals("securepass", user.getPassword());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void testToStringExcludesPassword() {
        User user = new User(
                1L,
                "John",
                25,
                "9876543210",
                "john@example.com",
                "john123",
                "secret",
                "USER"
        );

        String userString = user.toString();

        assertFalse(userString.contains("secret")); 
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User(1L, "John", 25, "123", "john@mail.com", "john", "pass", "USER");
        User user2 = new User(1L, "John", 25, "123", "john@mail.com", "john", "pass", "USER");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}