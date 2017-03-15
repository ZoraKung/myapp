package com.wjxinfo.core.base.utils.dialect;

import org.hibernate.dialect.SQLServer2012Dialect;

import java.sql.Types;

/**
 * Author: Jack
 * Date: 14-12-31
 * Description: Ms sql server 2014
 */
public class SQLServer2014Dialect extends SQLServer2012Dialect {
    public SQLServer2014Dialect() {
        super();
        registerHibernateType(Types.NVARCHAR, "string");
        registerHibernateType(Types.FLOAT, "big_decimal");
        registerHibernateType(Types.LONGNVARCHAR, "string");
    }
}
