package com.wjxinfo.core.base.dao;

import com.wjxinfo.core.base.utils.mybatis.MyBatisRepository;
import com.wjxinfo.core.base.vo.KeyValue;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Walter on 2016/1/29.
 */

@Repository("generalStoredProcedureMapper")
@MyBatisRepository
public interface GeneralStoredProcedureMapper<T extends Object> {

    @Select(value = "{call dbo.usp_utl_gateway ( " +
            "                #{xmlParameters,jdbcType=VARCHAR} " +
            "        ) " +
            "        }")
    @Options(statementType = StatementType.CALLABLE)
    @ResultType(value = KeyValue.class)
    List<KeyValue> getKeyValues(@Param("xmlParameters") String xmlParameters);

    @Select(value = "{call dbo.usp_utl_gateway ( " +
            "                #{xmlParameters,jdbcType=VARCHAR} " +
            "        ) " +
            "        }")
    @Options(statementType = StatementType.CALLABLE)
    @ResultType(value = Map.class)
    List<Map<String, Object>> getCustomerResult(@Param("xmlParameters") String xmlParameters);
}
