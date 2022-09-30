package com.example.chattproject.service;

import com.example.chattproject.domain.entity.Role;
import com.example.chattproject.domain.entity.User;
import com.example.chattproject.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}