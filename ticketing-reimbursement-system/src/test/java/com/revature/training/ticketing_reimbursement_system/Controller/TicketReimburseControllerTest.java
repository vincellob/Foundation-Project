package com.revature.training.ticketing_reimbursement_system.Controller;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
import com.revature.training.ticketing_reimbursement_system.Entity.Ticket;
import com.revature.training.ticketing_reimbursement_system.Service.UserService;
import com.revature.training.ticketing_reimbursement_system.Service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketReimburseControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketReimburseController ticketReimburseController;

    private User user;
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("employee1");
        user.setPassword("password123");

        ticket = new Ticket();
        ticket.setAmount(BigDecimal.valueOf(100.00));
        ticket.setStatus(Ticket.Status.PENDING);
    }

    @Test
    public void testRegisterUser_successful() {
        when(userService.registerUser(user)).thenReturn(user);
        ResponseEntity<?> response = ticketReimburseController.registerUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testRegisterUser_failed_invalidUser() {
        when(userService.registerUser(user)).thenThrow(new IllegalArgumentException("Username cannot be null or empty."));

        ResponseEntity<?> response = ticketReimburseController.registerUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username cannot be null or empty.", response.getBody());
    }

    @Test
    public void testLoginVerify_successful() {
        when(userService.login(user)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        ResponseEntity<?> response = ticketReimburseController.loginVerify(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testSubmitTicket_successful() {
        when(ticketService.submitTicket(ticket, "employee1")).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketReimburseController.submitTicket(ticket, "employee1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    public void testSubmitTicket_failed() {
        when(ticketService.submitTicket(ticket, "employee1")).thenThrow(new RuntimeException("Ticket submission failed."));

        ResponseEntity<Ticket> response = ticketReimburseController.submitTicket(ticket, "employee1");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetPendingTickets() {
        List<Ticket> tickets = Collections.singletonList(ticket);
        when(ticketService.getPendingTickets()).thenReturn(tickets);

        List<Ticket> response = ticketReimburseController.getPendingTickets();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ticket, response.get(0));
    }

    @Test
    public void testProcessTicket_successful() {
        Integer ticketId = 1;
        Ticket.Status status = Ticket.Status.APPROVED;
        when(ticketService.processTicket(ticketId, status)).thenReturn(null);

        ResponseEntity<String> response = ticketReimburseController.processTicket(ticketId, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket processed successfully.", response.getBody());
    }

    @Test
    public void testProcessTicket_failed_invalidStatus() {
        Integer ticketId = 1;
        Ticket.Status status = Ticket.Status.DENIED;
        when(ticketService.processTicket(ticketId, status)).thenThrow(new IllegalStateException("Invalid ticket status."));

        ResponseEntity<String> response = ticketReimburseController.processTicket(ticketId, status);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid ticket status.", response.getBody());
    }

    @Test
    public void testProcessTicket_failed_internalError() {
        Integer ticketId = 1;
        Ticket.Status status = Ticket.Status.APPROVED;
        when(ticketService.processTicket(ticketId, status)).thenThrow(new RuntimeException("Internal error"));

        ResponseEntity<String> response = ticketReimburseController.processTicket(ticketId, status);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while processing the ticket.", response.getBody());
    }

    @Test
    public void testGetAllTickets() {
        List<Ticket> tickets = Collections.singletonList(ticket);
        when(ticketService.getAllTickets()).thenReturn(tickets);

        List<Ticket> response = ticketReimburseController.getAllTickets();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ticket, response.get(0));
    }
}