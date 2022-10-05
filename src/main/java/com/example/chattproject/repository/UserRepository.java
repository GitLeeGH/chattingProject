package com.example.chattproject.repository;

import com.example.chattproject.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
//    List<Account> findByIdoitCountGreaterThan(int idiotCount);


}