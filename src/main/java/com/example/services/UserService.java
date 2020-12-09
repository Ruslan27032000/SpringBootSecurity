package com.example.services;

import com.example.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);

    Users createUser(Users user);

    Users saveUser(Users user);

    Users saveFile(Users user);
}
