package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="full_name")
    private String fullName;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;
}
