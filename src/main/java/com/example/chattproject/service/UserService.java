package com.example.chattproject.service;

import com.example.chattproject.domain.entity.Role;
import com.example.chattproject.domain.entity.User;
import com.example.chattproject.repository.RoleRepository;
import com.example.chattproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        User getUser = userRepository.findByEmail(user.getEmail());
        System.out.println(getUser);
        Role role = new Role();
        role.setId(getUser.getId());
        role.setRolename("ROLE_USER");
        System.out.println(role);
        roleRepository.save(role);
    }


    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

}