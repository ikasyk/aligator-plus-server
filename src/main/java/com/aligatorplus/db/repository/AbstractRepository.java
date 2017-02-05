package com.aligatorplus.db.repository;

import com.aligatorplus.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Project AligatorPlus
 * Created by igor, 31.01.17 20:22
 */

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
