package com.example.Brainstorm.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.Brainstorm.DTO.Urequest;
import com.example.Brainstorm.Models.User;
import com.example.Brainstorm.Repositories.UserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private User user;
    private Urequest urequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setAge(22);
        user.setNumber("9876543210");
        user.setEmail("john@example.com");
        user.setUsername("john123");
        user.setPassword("pass123");
        user.setRole("USER");

        urequest = new Urequest();
        urequest.setName("John Updated");
        urequest.setAge(25);
        urequest.setEmail("johnupdated@example.com");
        urequest.setNumber("9999999999");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user, new User());
        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void testGetUserByIdFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserbyId(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(userRepo).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        User result = userService.getUserbyId(1L);

        assertNull(result);
        verify(userRepo).findById(1L);
    }

    @Test
    void testGetUserByUsername() {
        when(userRepo.findByUsername("john123")).thenReturn(user);

        User result = userService.getUserbyUsername("john123");

        assertNotNull(result);
        assertEquals("john123", result.getUsername());
        verify(userRepo).findByUsername("john123");
    }

    @Test
    void testUpdateUserFound() {
        when(userRepo.findByUsername("john123")).thenReturn(user);
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.Updateuser("john123", urequest);

        assertNotNull(updatedUser);
        assertEquals("John Updated", updatedUser.getName());
        assertEquals(25, updatedUser.getAge());
        assertEquals("johnupdated@example.com", updatedUser.getEmail());
        assertEquals("9999999999", updatedUser.getNumber());

        verify(userRepo).findByUsername("john123");
        verify(userRepo).save(user);
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepo.findByUsername("john123")).thenReturn(null);

        User result = userService.Updateuser("john123", urequest);

        assertNull(result);
        verify(userRepo).findByUsername("john123");
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepo).deleteById(1L);

        userService.Deleteuser(1L);

        verify(userRepo, times(1)).deleteById(1L);
    }

    @Test
    void testLoadUserByUsernameFound() {
        when(userRepo.findByUsername("john123")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("john123");

        assertNotNull(userDetails);
        assertEquals("john123", userDetails.getUsername());
        assertEquals("pass123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(userRepo).findByUsername("john123");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepo.findByUsername("unknown")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("unknown");
        });

        verify(userRepo).findByUsername("unknown");
    }

    @Test
    void testLoadUserByUsernameDefaultRoleWhenRoleIsNull() {
        user.setRole(null);
        when(userRepo.findByUsername("john123")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("john123");

        assertNotNull(userDetails);
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(userRepo).findByUsername("john123");
    }
}