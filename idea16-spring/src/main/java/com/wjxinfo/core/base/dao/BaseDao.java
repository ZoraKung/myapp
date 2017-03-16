package com.wjxinfo.core.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Jack on 14-4-28.
 */
@NoRepositoryBean
public interface BaseDao<E> extends JpaRepository<E, String>, JpaSpecificationExecutor<E> {
}
