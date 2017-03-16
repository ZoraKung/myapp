package com.wjxinfo.core.auth.service;

import com.wjxinfo.core.auth.dao.ParameterDao;
import com.wjxinfo.core.auth.model.Parameter;
import com.wjxinfo.core.base.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("parameterManager")
public class ParameterManager extends BaseManager<Parameter> {
    @Qualifier("parameterDao")
    @Autowired
    private ParameterDao parameterDao;

    @Override
    public Parameter save(Parameter parameter) {
        parameterDao.save(parameter);
        return parameter;
    }

    public String getValueByName(String name) {
        Parameter parameter = parameterDao.findFirstByName(name);
        if (parameter != null) {
            return parameter.getValue();
        }
        return "";
    }
}
