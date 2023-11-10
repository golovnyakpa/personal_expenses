package com.github.golovnyakpa.personal_expenses.services.auth;

import com.github.golovnyakpa.personal_expenses.dao.entities.auth.Role;
import com.github.golovnyakpa.personal_expenses.dao.repositories.auth.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("USER").get();
    }

}
