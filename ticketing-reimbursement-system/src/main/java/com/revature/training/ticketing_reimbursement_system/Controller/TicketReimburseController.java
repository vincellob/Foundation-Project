package com.revature.training.ticketing_reimbursement_system.Controller;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Service.UserService;
import com.revature.training.ticketing_reimbursement_system.Service.TicketService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketReimburseController {

    private UserService userService;
    private TicketService ticketService;

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

    @PostMapping("/submit")
    public ResponseEntity<Ticket> submitTicket(
        @RequestBody Ticket ticket,
        @RequestParam("username") String username) {
    try {
        Ticket submittedTicket = ticketService.submitTicket(ticket, username);
        return ResponseEntity.ok(submittedTicket);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(null);
    }
    }

    @GetMapping("/pending")
    public List<Ticket> getPendingTickets() {
        return ticketService.getPendingTickets();
    }

    @PostMapping("/process/{ticketId}")
    public ResponseEntity<String> processTicket(@PathVariable Integer ticketId, @RequestParam Ticket.Status status) {
        try {
            ticketService.processTicket(ticketId, status);
            return ResponseEntity.ok("Ticket processed successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the ticket.");
        }
    }

    @GetMapping("/allTickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }
}