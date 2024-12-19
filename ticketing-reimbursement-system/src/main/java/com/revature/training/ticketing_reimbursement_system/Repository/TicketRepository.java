package com.revature.training.ticketing_reimbursement_system.Repository;

import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByEmployee(User employee);
    List<Ticket> findByStatus(Ticket.Status status);
    List<Ticket> findByStatus(String status);
}