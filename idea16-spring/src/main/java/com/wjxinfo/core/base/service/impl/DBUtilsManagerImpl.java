package com.wjxinfo.core.base.service.impl;

import com.wjxinfo.core.base.dao.DatabaseUtilsMapper;
import com.wjxinfo.core.base.service.DBUtilsManager;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.vo.LabelValue;
import com.wjxinfo.core.base.vo.SqlDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WTH on 2014/5/23.
 * <p>
 * Access DB via SQL Directly
 */

@Service("dbUtilsManager")
public class DBUtilsManagerImpl implements DBUtilsManager {
    private static final Logger logger = LoggerFactory.getLogger(DBUtilsManagerImpl.class);

    private final static String DIRECT_SQL = "directSql";

    private final static String PARA_USER_ID = ":userId";

    private final static String PARA_PK_ID = ":pkId";

    private final static String PARA_FK_ID = ":fkId";

    private final static String PARA_FORMAL_SIGNAL = "\\?";

    private boolean isStringID = true;

    @Qualifier(value = "databaseUtilsMapper")
    @Autowired
    private DatabaseUtilsMapper databaseUtilsMapper;

    @Qualifier(value = "sqlDefinition")
    @Autowired
    private SqlDefinition sqlDefinition;

    public SqlDefinition getSqlDefinition() {
        return sqlDefinition;
    }

    public void setSqlDefinition(SqlDefinition sqlDefinition) {
        this.sqlDefinition = sqlDefinition;
    }

    public boolean isPrimaryKeyReferenced(String key, String fkId) {
        String sql = this.getPreDefinedSql(key);
        if (StringUtils.isNotBlank(sql)) {
            if (StringUtils.isNotBlank(fkId)) {
                if (isStringID) {
                    fkId = "'" + fkId + "'";
                }
                sql = sql.replaceAll(PARA_FK_ID, fkId);
            } else {
                sql = sql.replaceAll(PARA_FK_ID, "null");
            }

            long count = this.count(sql);

            return count > 0 ? true : false;
        }

        return false;
    }

    private String getPreDefinedSql(String key) {
        if (sqlDefinition != null && key != null) {
            String sql = sqlDefinition.getPreDefinedSql().get(key);
            if (logger.isInfoEnabled()) {
                logger.info("SQL = " + sql);
            }
            return sql;
        } else {
            logger.error("Pre-defined SQL or key is null.");
        }

        return null;
    }

    private boolean includeCUDsql(String sql) {
        if (sql == null) {
            return false;
        }
        if (sql.toLowerCase().indexOf("insert ") >= 0) {
            return true;
        }
        if (sql.toLowerCase().indexOf("update ") >= 0) {
            return true;
        }
        if (sql.toLowerCase().indexOf("delete ") >= 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isDuplicated(String key, String pkId, String[] args) {
        String sql = this.getPreDefinedSql(key);
        if (StringUtils.isNotBlank(sql)) {
            if (StringUtils.isNotBlank(pkId)) {
                if (isStringID) {
                    pkId = "'" + pkId + "'";
                }
                sql = sql.replaceAll(PARA_PK_ID, pkId);
            } else {
                sql = sql.replaceAll(PARA_PK_ID, "null");
            }

            if (args != null) {
                logger.info("args length = " + args.length);
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null) {
                        String para = PARA_FORMAL_SIGNAL + (i + 1);
                        logger.info(para + " >> " + args[i]);
                        sql = sql.replaceAll(para, args[i]);
                    }
                }
            }

            long count = this.count(sql);

            return count > 0 ? true : false;
        }

        return false;
    }

    public List<LabelValue> getLabelValues(String key) {
        List<LabelValue> result = new ArrayList<LabelValue>();

        if (StringUtils.isBlank(key)) {
            return result;
        }

        String sql = this.getLabelValueSql(key);
        if (sql != null && !sql.equals("")) {
            List<LabelValue> list = getLabelValuesBySql(sql);
            return list;
        }

        return result;
    }

    private String getLabelValueSql(String key) {
        if (sqlDefinition != null && key != null) {
            String sql = sqlDefinition.getLabelValueSql().get(key);
            if (logger.isInfoEnabled()) {
                logger.info("SQL = " + sql);
            }
            return sql;
        } else {
            logger.error("Label Value SQL or key is null.");
        }

        return null;
    }

    public List<LabelValue> getLabelValues(String key, String userId) {
        List<LabelValue> result = new ArrayList<LabelValue>();

        if (StringUtils.isBlank(key)) {
            return result;
        }

        String sql = this.getLabelValueSql(key);
        if (StringUtils.isNotBlank(sql)) {
            if (StringUtils.isNotBlank(userId)) {
                if (isStringID) {
                    userId = "'" + userId + "'";
                }
                sql = sql.replaceAll(PARA_USER_ID, userId);
            }else {
                sql = sql.replaceAll(PARA_USER_ID, "null");
            }

            List<LabelValue> list = getLabelValuesBySql(sql);
            return list;
        }

        return result;
    }

    @Override
    public List<LabelValue> getLabelValues(String key, String[] args) {
        List<LabelValue> result = new ArrayList<LabelValue>();

        if (StringUtils.isBlank(key)) {
            return result;
        }

        String sql = this.getLabelValueSql(key);
        if (StringUtils.isNotBlank(sql)) {
            if (args != null) {
                logger.info("args length = " + args.length);
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null) {
                        String para = PARA_FORMAL_SIGNAL + (i + 1);
                        logger.info(para + " >> " + args[i]);
                        sql = sql.replaceAll(para, args[i]);
                    }
                }
            }

            List<LabelValue> list = getLabelValuesBySql(sql);
            return list;
        }

        return result;
    }

    public List<LabelValue> getLabelValuesBySql(String sql) {
        if (includeCUDsql(sql)) {
            return null;
        }
        logger.info("SQL = " + sql);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DIRECT_SQL, sql);

        return databaseUtilsMapper.selectLabelValues(map);
    }

    public long count(String sql) {
        if (includeCUDsql(sql)) {
            return -1;
        }
        logger.info("SQL = " + sql);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DIRECT_SQL, sql);

        return databaseUtilsMapper.count(map);
    }

    public int create(String sql) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DIRECT_SQL, sql);

        return databaseUtilsMapper.insert(map);
    }

    public int update(String sql) {
        if (logger.isDebugEnabled()) {
            logger.debug(sql);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DIRECT_SQL, sql);

        return databaseUtilsMapper.update(map);
    }

    public int delete(String sql) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DIRECT_SQL, sql);

        return databaseUtilsMapper.delete(map);
    }

}
