package com.aligatorplus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class AbstractEntity implements Serializable {
    public abstract Long getId();
    public abstract void setId(Long id);

    @Override
    public String toString() {
        return String.format("Entity.%s{id=%d}", getClass().getSimpleName(), getId());
    }
}
