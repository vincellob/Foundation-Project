package com.revature.training.ticketing_reimbursement_system.Service;

import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Repository.TicketRepository;
import com.revature.training.ticketing_reimbursement_system.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    
}