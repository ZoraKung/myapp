package com.wjxinfo.common.master.dao;

import com.wjxinfo.common.master.model.MasterType;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by MZJ on 14-5-12.
 */
@Repository("masterTypeDao")
public interface MasterTypeDao extends BaseDao<MasterType> {

    @Query("select o from MasterType o where o.type = ?1 order by o.seq, o.type")
    List<MasterType> findByType(String type);

}
