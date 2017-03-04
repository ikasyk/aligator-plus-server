package com.aligatorplus.db.service.impl;

import com.aligatorplus.db.service.UserService;
import com.aligatorplus.db.repository.UserRepository;
import com.aligatorplus.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
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
