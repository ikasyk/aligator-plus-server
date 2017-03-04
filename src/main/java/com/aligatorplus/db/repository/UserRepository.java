package com.aligatorplus.db.repository;

import com.aligatorplus.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends AbstractRepository<User> {

    @Query("select u from User u where u.login = ?1")
    User findByLogin(String login);

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);

//    List<User> findAll();
}
