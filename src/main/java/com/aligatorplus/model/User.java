package com.aligatorplus.model;

import javax.persistence.*;

/**
 * Project AligatorPlus
 * Created by igor, 30.01.17 15:39
 */

@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String login;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    public User() {
        this.id = 0L;
        this.login = null;
        this.email = null;
        this.password = null;
    }

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, login='%s', email='%s'}", id, login, email);
    }
}
