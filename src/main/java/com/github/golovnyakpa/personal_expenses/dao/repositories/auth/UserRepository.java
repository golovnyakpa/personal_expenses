package com.github.golovnyakpa.personal_expenses.dao.repositories.auth;

import com.github.golovnyakpa.personal_expenses.dao.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
