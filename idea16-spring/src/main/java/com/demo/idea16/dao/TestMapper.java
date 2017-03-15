package com.demo.idea16.dao;

import com.demo.idea16.vo.TestVo;
import com.wjxinfo.core.base.utils.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by Zora on 2017/3/15.
 */
@Repository("testMapper")
@MyBatisRepository
public interface TestMapper {
    
    @Select("SELECT TOP 1 * FROM dbo.view_test_form WHERE id = #{id}")
    TestVo findTestVoById(@Param("id") String id);
}
