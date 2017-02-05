package com.aligatorplus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Project AligatorPlus
 * Created by igor, 31.01.17 16:05
 */

@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class AbstractEntity implements Serializable {
//    @Column(unique = true)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id protected Long id;
//
    public abstract Long getId();
    public abstract void setId(Long id);

    @Override
    public String toString() {
        return String.format("Entity.%s{id=%d}", getClass().getSimpleName(), getId());
    }
}
