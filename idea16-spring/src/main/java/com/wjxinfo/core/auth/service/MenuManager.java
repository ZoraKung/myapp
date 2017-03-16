package com.wjxinfo.core.auth.service;

import com.wjxinfo.core.auth.dao.MenuDao;
import com.wjxinfo.core.auth.model.Menu;
import com.wjxinfo.core.base.service.BaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by WTH on 2014/5/7.
 */
@Service("menuManager")
public class MenuManager extends BaseManager<Menu> {
    private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);

    @Autowired
    @Qualifier("menuDao")
    private MenuDao menuDao;

    public List<Menu> getAllMenus() {
        return menuDao.findAllMenus();
    }

    public Menu getByUrl(String url) {
        return menuDao.findByUrl(url);
    }
}
