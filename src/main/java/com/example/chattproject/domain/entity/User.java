package com.example.chattproject.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    //    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
//    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_role", //조인 테이블명
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), //외래키
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")) //반대 외래키
    private List<Role> roles;
}
