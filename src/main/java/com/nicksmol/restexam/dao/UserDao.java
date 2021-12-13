package com.nicksmol.restexam.dao;



import com.nicksmol.restexam.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();

    User selectById(long id);

    void save(User user);

    void update(User user, long id);

    void delete(long id);

    User findByUsername(String username);

    User findByUserEmail(String email);

    User findByEmail(String email);

}
