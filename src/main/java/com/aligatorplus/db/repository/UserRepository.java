package com.aligatorplus.db.repository;

import com.aligatorplus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project AligatorPlus
 * Created by igor, 31.01.17 16:31
 */

@Repository
public interface UserRepository extends AbstractRepository<User> {

    @Query("select u from User u where u.id = ?1")
    User findById(Long id);

    @Query("select u from User u where u.login = ?1")
    User findByLogin(String login);

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);


    List<User> findAll();
}
