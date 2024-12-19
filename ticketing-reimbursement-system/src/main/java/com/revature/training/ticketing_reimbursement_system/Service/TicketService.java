package com.revature.training.ticketing_reimbursement_system.Service;

import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Repository.TicketRepository;
import com.revature.training.ticketing_reimbursement_system.Repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket submitTicket(Ticket ticket, String username) {
        Optional<User> employeeOpt = userRepository.findOptionalByUsername(username);

        if (employeeOpt.isPresent()) {
            User employee = employeeOpt.get();
            ticket.setEmployee(employee);
            ticket.setStatus(Ticket.Status.PENDING);
            return ticketRepository.save(ticket);
        } else {
            throw new RuntimeException("User not found given username.");
        }
    }

    public List<Ticket> getPendingTickets() {
        return ticketRepository.findByStatus(Ticket.Status.PENDING);
    }

    public Ticket processTicket(Integer ticketId, Ticket.Status status) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getStatus().equals(Ticket.Status.PENDING)) {
            throw new IllegalStateException("Ticket has already been processed.");
        }

        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return (List<Ticket>) ticketRepository.findAll();
    }


}