package com.revature.training.ticketing_reimbursement_system.Repository;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}