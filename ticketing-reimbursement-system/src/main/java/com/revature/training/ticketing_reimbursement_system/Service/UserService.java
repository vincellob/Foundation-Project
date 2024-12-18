package com.revature.training.ticketing_reimbursement_system.Service;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    //@Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    

    public ResponseEntity<User> login(User user){
        Optional<User> accountExists = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (accountExists.isPresent()){
            User verify = accountExists.get();
            return new ResponseEntity<>(verify, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    
    public User registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (user.getRole() == null) {
            user.setRole(User.Role.EMPLOYEE);
        }

        return userRepository.save(user);
    }
}