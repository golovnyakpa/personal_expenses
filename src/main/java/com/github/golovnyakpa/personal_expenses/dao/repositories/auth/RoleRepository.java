package com.github.golovnyakpa.personal_expenses.dao.repositories.auth;

import com.github.golovnyakpa.personal_expenses.dao.entities.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
