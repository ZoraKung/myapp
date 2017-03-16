package com.wjxinfo.core.auth.dao;

import com.wjxinfo.core.auth.model.User;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "userDao")
public interface UserDao extends BaseDao<User> {
    @Query("select o from User o left join fetch o.roles p where o.loginName = ?1")
    List<User> findByLoginName(String loginName);

    @Query("select o from User o  where o.loginName = ?1 and isnull(o.deleteFlag,0) = 0")
    List<User> findByLogin(String loginName);

    @Query("select o from User o  where o.email = ?1")
    List<User> findByEmail(String email);

    List<User> findByLoginNameAndPwd(String loginName, String pwd);

    @Query("select o from User o left join fetch o.roles p where p.id = ?1")
    List<User> getUsersByRoleId(String roleId);

    @Query("select o from User o left join fetch o.roles p where p.name = ?1")
    List<User> getUsersByRoleName(String roleName);

    @Query("select distinct o from User o left join fetch o.roles p where o.id = ?1 and p.isAdministrator = ?2")
    User getUserByIsAdmin(String id, Boolean isAdmin);

    @Query("select o from User o  where o.adUserName = ?1 and (o.status = '1' or o.status = 'Y') ")
    List<User> findByAdUserName(String adUserName);

    // BT 10722
    @Query("select o from User o left join fetch o.roles p where o.loginName = ?1 and  (o.status = '1' or o.status = 'Y') ")
    User findFirstByLoginNameForAuth(String loginName);

    // BT 10722
    @Query("select o from User o  where o.adUserName = ?1 and  (o.status = '1' or o.status = 'Y') ")
    User findFirstByAdUserNameForAuth(String adUserName);

    // BT 10722
    @Query("select o from User o where (o.loginName = ?1 or o.adUserName = ?1) and  (o.status = '1' or o.status = 'Y') ")
    User findFirstByLocalUserNameOrAdUserNameForAuth(String loginName);
}
