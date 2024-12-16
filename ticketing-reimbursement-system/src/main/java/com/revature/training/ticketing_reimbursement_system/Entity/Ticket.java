package com.revature.training.ticketing_reimbursement_system.Entity;
import jakarta.persistence.*;


@Entity
@Table(name="ticket")
public class Ticket {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ticketId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User employee;

    
    public Ticket() {
    }

 
    public Ticket(Double amount, String description, User employee) {
        this.amount = amount;
        this.description = description;
        this.employee = employee;
        this.status = Status.PENDING; 
    }


    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }


    public enum Status {
        PENDING,
        APPROVED,
        DENIED
    }
}


