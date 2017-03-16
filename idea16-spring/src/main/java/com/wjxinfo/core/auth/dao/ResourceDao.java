package com.wjxinfo.core.auth.dao;

import com.wjxinfo.core.auth.model.Resource;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "resourceDao")
public interface ResourceDao extends BaseDao<Resource> {

    @Query("select o from Resource o  where o.status = '1' order by o.seq")
    List<Resource> findAll();
}
