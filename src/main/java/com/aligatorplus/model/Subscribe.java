package com.aligatorplus.model;

import javax.persistence.*;

//@Entity
//@Table(name = "subscribes")
public class Subscribe extends AbstractEntity {
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
