package com.aligatorplus.db.service;

import com.aligatorplus.model.User;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.List;

public interface UserService {
    void create(User entity);
    List<User> findAll();
    User findById(Long id);
    User findByLogin(String login);
    User findByEmail(String email);
    void update(User entity);
    void delete(User entity);
}
