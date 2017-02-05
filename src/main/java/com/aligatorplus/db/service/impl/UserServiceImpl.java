package com.aligatorplus.db.service.impl;

import com.aligatorplus.db.service.UserService;
import com.aligatorplus.db.repository.UserRepository;
import com.aligatorplus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Project AligatorPlus
 * Created by igor, 01.02.17 14:42
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public void create(User entity) {
        userRepository.saveAndFlush(entity);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void update(User entity) {
        userRepository.save(entity);
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }
}
