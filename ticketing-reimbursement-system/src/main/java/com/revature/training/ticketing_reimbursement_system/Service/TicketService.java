package com.revature.training.ticketing_reimbursement_system.Service;

import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Repository.TicketRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    //@Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket submitTicket(User employee, double amount, String description) {
       
        Ticket ticket = new Ticket();
        ticket.setAmount(amount);
        ticket.setDescription(description);
        ticket.setStatus(Ticket.Status.PENDING);
        ticket.setEmployee(employee); 
        return ticketRepository.save(ticket);  
    }


    public List<Ticket> findTicketsByStatus(Ticket.Status status) {
        return ticketRepository.findByStatus(status);
    }


    public List<Ticket> findTicketsByEmployee(User employee) {
        return ticketRepository.findByEmployee(employee);
    }

    public Ticket processTicket(Integer ticketId, Ticket.Status status) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

     
        if (ticket.getStatus() != Ticket.Status.PENDING) {
            throw new RuntimeException("Ticket has already been processed.");
        }

        ticket.setStatus(status); 
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

}