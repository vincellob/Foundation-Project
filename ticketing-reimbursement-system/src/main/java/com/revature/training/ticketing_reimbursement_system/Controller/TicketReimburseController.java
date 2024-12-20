package com.revature.training.ticketing_reimbursement_system.Controller;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Service.UserService;
import com.revature.training.ticketing_reimbursement_system.Service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketReimburseController {

    private static final Logger logger = LoggerFactory.getLogger(TicketReimburseController.class);

    private UserService userService;
    private TicketService ticketService;

    TicketReimburseController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        logger.info("Received request to register user: {}", user.getUsername());
        try {
            User registeredUser = userService.registerUser(user);
            logger.info("User successfully registered: {}", registeredUser.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException e) {
            logger.error("Error registering user {}: {}", user.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginVerify(@RequestBody User user){
        logger.info("Received login request for user: {}", user.getUsername());
        return userService.login(user);
    }

    @PostMapping("/submit")
    public ResponseEntity<Ticket> submitTicket(
        @RequestBody Ticket ticket,
        @RequestParam("username") String username) {
        logger.info("Received request to submit ticket for user: {} with amount: {}", username, ticket.getAmount());
        try {
            Ticket submittedTicket = ticketService.submitTicket(ticket, username);
            logger.info("Ticket successfully submitted: {}", submittedTicket.getTicketId());
            return ResponseEntity.ok(submittedTicket);
        } catch (Exception e) {
            logger.error("Error submitting ticket for user {}: {}", username, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/pending")
    public List<Ticket> getPendingTickets() {
        logger.info("Received request to get pending tickets.");
        return ticketService.getPendingTickets();
    }

    @PostMapping("/process/{ticketId}")
    public ResponseEntity<String> processTicket(@PathVariable Integer ticketId, @RequestParam Ticket.Status status) {
        logger.info("Received request to process ticket with ID: {} and status: {}", ticketId, status);
        try {
            ticketService.processTicket(ticketId, status);
            logger.info("Ticket ID: {} processed successfully with status: {}", ticketId, status);
            return ResponseEntity.ok("Ticket processed successfully.");
        } catch (IllegalStateException e) {
            logger.error("Error processing ticket ID: {}: {}", ticketId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal error processing ticket ID: {}: {}", ticketId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the ticket.");
        }
    }

    @GetMapping("/allTickets")
    public List<Ticket> getAllTickets() {
        logger.info("Received request to get all tickets.");
        return ticketService.getAllTickets();
    }
}