package com.wjxinfo.core.auth.dao;

import com.wjxinfo.core.auth.model.Parameter;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("parameterDao")
public interface ParameterDao extends BaseDao<Parameter> {
    @Query("select o from Parameter o  where o.name = ?1")
    Parameter findByName(String name);

    Parameter findFirstByName(String name);
}

