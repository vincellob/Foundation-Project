package com.revature.training.ticketing_reimbursement_system.Service;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("employee1");
        user.setPassword("password123");
        user.setRole(User.Role.EMPLOYEE);
    }

    @Test
    public void testLogin_successful() {
        when(userRepository.findByUsernameAndPassword("employee1", "password123"))
                .thenReturn(Optional.of(user));

        ResponseEntity<User> response = userService.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("employee1", response.getBody().getUsername());
    }

    @Test
    public void testLogin_unsuccessful() {
        when(userRepository.findByUsernameAndPassword("employee1", "wrongpassword"))
                .thenReturn(Optional.empty());

        ResponseEntity<User> response = userService.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testRegisterUser_successful() {
        when(userRepository.existsByUsername("employee1")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("employee1", registeredUser.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    public void testRegisterUser_usernameAlreadyExists() {
        when(userRepository.existsByUsername("employee1")).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });
        assertEquals("Username already exists.", thrown.getMessage());
    }

    @Test
    public void testRegisterUser_emptyUsername() {
        user.setUsername("");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });
        assertEquals("Username cannot be null or empty.", thrown.getMessage());
    }

    @Test
    public void testRegisterUser_emptyPassword() {
        user.setPassword("");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });
        assertEquals("Password cannot be null or empty.", thrown.getMessage());
    }
}