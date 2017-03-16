package com.wjxinfo.core.auth.dao;

import com.wjxinfo.core.auth.model.Menu;
import com.wjxinfo.core.base.dao.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by WTH on 2014/5/7.
 */
@Repository(value = "menuDao")
public interface MenuDao extends BaseDao<Menu> {
    @Query("select o from Menu o where o.status = '1' order by o.seq")
    List<Menu> findAvailableMenus();

    @Query("select o from Menu o order by o.seq")
    List<Menu> findAllMenus();

    @Query("select m from Menu m where m.url = ?1")
    Menu findByUrl(String url);
}
