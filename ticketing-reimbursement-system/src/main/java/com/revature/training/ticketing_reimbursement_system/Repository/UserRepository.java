package com.revature.training.ticketing_reimbursement_system.Repository;

import com.revature.training.ticketing_reimbursement_system.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);
    
    User findByUsername(String username);

    Optional<User> findOptionalByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE username=?1 AND password=?2", nativeQuery=true)
    public Optional<User> findByUsernameAndPassword(String username, String password);
}