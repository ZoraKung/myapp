package com.wjxinfo.core.auth.service;

import com.wjxinfo.core.auth.dao.ResourceDao;
import com.wjxinfo.core.auth.model.Resource;
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
@Service("resourceManager")
public class ResourceManager extends BaseManager<Resource> {
    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    @Autowired
    @Qualifier("resourceDao")
    private ResourceDao resourceDao;

    public List<Resource> getAllResources() {
        return resourceDao.findAll();
    }
}
