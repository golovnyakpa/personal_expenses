package com.github.golovnyakpa.personal_expenses.services.auth;

import com.github.golovnyakpa.personal_expenses.configs.PasswordEncoderConfig;
import com.github.golovnyakpa.personal_expenses.dao.entities.auth.User;
import com.github.golovnyakpa.personal_expenses.dao.repositories.auth.UserRepository;
import com.github.golovnyakpa.personal_expenses.dtos.request.auth.RegistrationUserDtoRq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoderConfig passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoderConfig passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDtoRq registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.passwordEncoder().encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }
}
