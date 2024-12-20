package com.revature.training.ticketing_reimbursement_system.Service;

import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Repository.TicketRepository;
import com.revature.training.ticketing_reimbursement_system.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    private TicketRepository ticketRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        ticketRepository = mock(TicketRepository.class);
        userRepository = mock(UserRepository.class);
        ticketService = new TicketService(ticketRepository, userRepository);
    }

    @Test
    public void testSubmitTicket_successful() {
        String username = "employee1";
        Ticket ticket = new Ticket();
        ticket.setAmount(BigDecimal.valueOf(100.00));
        ticket.setDescription("Test description");

        User employee = new User();
        employee.setUsername(username);

        when(userRepository.findOptionalByUsername(username)).thenReturn(Optional.of(employee));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket result = ticketService.submitTicket(ticket, username);

        assertNotNull(result);
        assertEquals(Ticket.Status.PENDING, result.getStatus());
        assertEquals(employee, result.getEmployee());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void testSubmitTicket_userNotFound() {
        String username = "nonexistent";
        Ticket ticket = new Ticket();
        ticket.setAmount(BigDecimal.valueOf(100.00));
        ticket.setDescription("Test description");

        when(userRepository.findOptionalByUsername(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.submitTicket(ticket, username);
        });

        assertEquals("User not found given username.", exception.getMessage());
    }

    @Test
    public void testGetPendingTickets() {
        Ticket ticket1 = new Ticket();
        ticket1.setStatus(Ticket.Status.PENDING);
        Ticket ticket2 = new Ticket();
        ticket2.setStatus(Ticket.Status.PENDING);

        when(ticketRepository.findByStatus(Ticket.Status.PENDING)).thenReturn(List.of(ticket1, ticket2));

        List<Ticket> tickets = ticketService.getPendingTickets();

        assertNotNull(tickets);
        assertEquals(2, tickets.size());
    }

    @Test
    public void testProcessTicket_successful() {
        Integer ticketId = 1;
        Ticket ticket = new Ticket();
        ticket.setStatus(Ticket.Status.PENDING);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket result = ticketService.processTicket(ticketId, Ticket.Status.APPROVED);

        assertNotNull(result);
        assertEquals(Ticket.Status.APPROVED, result.getStatus());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void testProcessTicket_ticketNotFound() {
        Integer ticketId = 1;

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.processTicket(ticketId, Ticket.Status.APPROVED);
        });

        assertEquals("Ticket not found", exception.getMessage());
    }

    @Test
    public void testProcessTicket_alreadyProcessed() {
        Integer ticketId = 1;
        Ticket ticket = new Ticket();
        ticket.setStatus(Ticket.Status.APPROVED);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            ticketService.processTicket(ticketId, Ticket.Status.DENIED);
        });

        assertEquals("Ticket has already been processed.", exception.getMessage());
    }

    @Test
    public void testGetAllTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        when(ticketRepository.findAll()).thenReturn(List.of(ticket1, ticket2));

        List<Ticket> tickets = ticketService.getAllTickets();

        assertNotNull(tickets);
        assertEquals(2, tickets.size());
    }
}