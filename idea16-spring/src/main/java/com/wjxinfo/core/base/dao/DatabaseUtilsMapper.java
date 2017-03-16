package com.wjxinfo.core.base.dao;

import com.wjxinfo.core.base.utils.mybatis.MyBatisRepository;
import com.wjxinfo.core.base.vo.LabelValue;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("databaseUtilsMapper")
@MyBatisRepository
public interface DatabaseUtilsMapper {

    // Direct SQL -- SQL Express
    List<LabelValue> selectLabelValues(Map<String, Object> map);

    long count(Map<String, Object> map);

    int insert(Map<String, Object> map);

    int update(Map<String, Object> map);

    int delete(Map<String, Object> map);
}
