package com.revature.training.ticketing_reimbursement_system.Repository;

import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByEmployee_Username(String username);

    // Custom query to find all tickets by status (PENDING, APPROVED, DENIED)
    List<Ticket> findByStatus(String status);

    
}