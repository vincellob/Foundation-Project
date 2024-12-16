package com.revature.training.ticketing_reimbursement_system.Controller;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Service.UserService;
import com.revature.training.ticketing_reimbursement_system.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketReimburseController {

    private UserService userService;
    private TicketService ticketService;

    @Autowired
    TicketReimburseController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    
    @PostMapping("/register")
    public User registerUser(@RequestParam String username, @RequestParam String password) {
        return userService.registerUser(username, password);
    }

    
    @PostMapping("/submitTicket")
    public Ticket submitTicket(@RequestParam Integer userId, @RequestParam double amount, @RequestParam String description) {
        User employee = userService.findUserByUsername(userId.toString());  // Find user by userId (you can change this lookup)
        return ticketService.submitTicket(employee, amount, description);
    }

    
    @PostMapping("/processTicket")
    public Ticket processTicket(@RequestParam Integer ticketId, @RequestParam String status) {
        Ticket.Status ticketStatus = Ticket.Status.valueOf(status.toUpperCase());
        return ticketService.processTicket(ticketId, ticketStatus);
    }

    
    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }
}