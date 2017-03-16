package com.wjxinfo.core.auth.dao;

import com.wjxinfo.core.auth.model.UserPreference;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by MZJ on 14-7-4.
 */
@Repository("userPreferenceDao")
public interface UserPreferenceDao extends BaseDao<UserPreference> {
    @Query("select o from UserPreference o left join o.user u where o.attribute = ?1 and u.id = ?2")
    List<UserPreference> findByAttributeAndUser(String attr, String userId);
}
