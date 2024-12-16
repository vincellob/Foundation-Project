package com.revature.training.ticketing_reimbursement_system.Entity;
import jakarta.persistence.*;

@Entity
@Table(name="user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        EMPLOYEE, MANAGER
    }


    public User(){

    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
  
    public User(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
 
    public Integer getUserId() {
        return userId;
    }
  
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

  
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}