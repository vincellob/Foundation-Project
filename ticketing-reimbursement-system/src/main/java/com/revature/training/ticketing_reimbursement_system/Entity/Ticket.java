package com.revature.training.ticketing_reimbursement_system.Entity;
import jakarta.persistence.*;


@Entity
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status = "PENDING"; // Default status

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    // Default constructor
    public Ticket() {
    }

    // Constructor with parameters
    public Ticket(String title, String description, Double amount, User employee) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.employee = employee;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }
}

