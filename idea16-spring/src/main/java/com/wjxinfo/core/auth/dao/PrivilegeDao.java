package com.wjxinfo.core.auth.dao;

import com.wjxinfo.core.auth.model.Privilege;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "privilegeDao")
public interface PrivilegeDao extends BaseDao<Privilege> {

    @Query("select o from Privilege o where o.identifier like ?1")
    List<Privilege> findPrivilegeByIdentifyLike(String privilegeIdentify);

    @Query("select o from Privilege o where o.identifier = ?1")
    List<Privilege> findPrivilegeByIdentify(String privilegeIdentify);

    @Query("select o from Privilege o where o.name = ?1")
    List<Privilege> findPrivilegeByName(String privilegeName);

    @Query("select distinct o from Privilege o left join o.roles r left join r.users u where u.id=?1")
    public List<Privilege> getPrivilegeList(String userId);

    @Query("select distinct (o.identifier) from Privilege o left join o.roles r left join r.users u left join o.users p where u.id=?1 or p.id =?1")
    public List<String> getPrivilegeStrings(String userId);
}
