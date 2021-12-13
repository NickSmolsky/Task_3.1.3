package com.nicksmol.restexam.service;

import com.nicksmol.restexam.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();

    void save(User user);

    User selectById(long id);

    void update(User user, long id);

    void delete(long id);

    User findByUsername(String username);

    User findByEmail(String email);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
