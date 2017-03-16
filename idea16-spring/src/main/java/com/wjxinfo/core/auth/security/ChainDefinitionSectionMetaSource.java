package com.wjxinfo.core.auth.security;

import com.wjxinfo.core.auth.model.Resource;
import com.wjxinfo.core.auth.service.ResourceManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.MessageFormat;

public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
    private static final Logger logger = LoggerFactory.getLogger(ChainDefinitionSectionMetaSource.class);

    private static final String PERMISSION_STRING = "perms[\"{0}\"]";

    private ResourceManager resourceManager;

    private String filterChainDefinitions;

    @Autowired
    public void setResourceManager(@Qualifier("resourceManager") ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Ini.Section getObject() throws BeansException {
        logger.info("\n\nURL and Permission Setup....");
        Ini ini = new Ini();

        ini.load(filterChainDefinitions);

        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }

        for (Resource resource : resourceManager.getAllResources()) {
            if (StringUtils.isNotEmpty(resource.getUrl())) {
                if (StringUtils.isNotEmpty(resource.getPermission())) {
                    if (StringUtils.isNotBlank(resource.getPlainFlag()) && resource.getPlainFlag().equals("1")) {
                        section.put(resource.getUrl(), MessageFormat.format(PERMISSION_STRING, resource.getPermission()));
                        logger.info(resource.getUrl() + "=" + MessageFormat.format(PERMISSION_STRING, resource.getPermission()));
                    } else {
                        section.put(resource.getUrl(), resource.getPermission());
                        logger.info(resource.getUrl() + "=" + resource.getPermission());
                    }
                }
            }
        }
        section.put("/**", "authc");
        logger.info("/**=authc");

        return section;
    }

    @Override
    public Class<?> getObjectType() {
        return Ini.Section.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
