package com.wjxinfo.core.auth.dao;

import com.wjxinfo.core.auth.model.Role;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "roleDao")
public interface RoleDao extends BaseDao<Role> {
    @Query("select o from Role o left join fetch o.privileges p where p.id = ?1")
    List<Role> getRolesByPrivilegeId(String privilegeId);

    @Query("select o from Role o where o.name = ?1")
    List<Role> findByName(String name);

    @Query("select o from Role o where o.isAdministrator = ?1")
    List<Role> findAdministratorRoles();

    @Query("select o from Role o left join fetch o.users p where p.id = ?1")
    List<Role> findRolesByUserId(String userId);

    @Query("select o from Role o where o.name = ?1")
    Role findOneByName(String roleName);
}
