package com.revature.training.ticketing_reimbursement_system.Controller;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
// import com.revature.training.ticketing_reimbursement_system.Repository.UserRepository;
import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Service.UserService;
import com.revature.training.ticketing_reimbursement_system.Service.TicketService;

import org.springframework.http.HttpStatus;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketReimburseController {

    private UserService userService;
    private TicketService ticketService;
    // private UserRepository userRepository;

    // @Autowired
    TicketReimburseController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
    try {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }


    
    @PostMapping("/login")
    public ResponseEntity<?> loginVerify(@RequestBody User user){
        return userService.login(user);
    }

    
}