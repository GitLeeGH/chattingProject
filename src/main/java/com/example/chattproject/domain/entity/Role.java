package com.example.chattproject.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private Long id; //index

    private String rolename; //id

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(String rolenames){
        this.rolename = rolenames;
    }

    public Role(Long id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    //    @ManyToMany(mappedBy = "roles")
//    private List<User> users;
}