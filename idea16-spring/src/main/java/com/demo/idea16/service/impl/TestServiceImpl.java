package com.demo.idea16.service.impl;

import com.demo.idea16.dao.TestMapper;
import com.demo.idea16.service.TestService;
import com.demo.idea16.vo.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Zora on 2017/3/15.
 */
@Service("testService")
public class TestServiceImpl implements TestService {

    @Autowired
    @Qualifier("testMapper")
    private TestMapper testMapper;

    @Override
    public TestVo findTestVoById(String id) {
        return testMapper.findTestVoById(id);
    }
}
