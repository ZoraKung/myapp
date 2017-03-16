package com.wjxinfo.core.base.model.utils;

/**
 * Created by WTH on 2015/10/19.
 */

import com.wjxinfo.core.base.constants.IdPrefix;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class FormatTableGenerator extends TableGenerator {
    private String format;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        type = new LongType();
        super.configure(type, params, serviceRegistry);
        format = params.getProperty("format");
    }

    @Override
    public Serializable generate(SessionImplementor session, Object obj) {

        Serializable generated = super.generate(session, obj);

        if (generated instanceof Number) {

            String customerFormatByTable = this.getCustomerFormat();
            if (StringUtils.isNotBlank(customerFormatByTable)) {
                return String.format(customerFormatByTable, generated);
            }

            if (format == null) {
                return String.valueOf(generated);
            }

            String prefix = this.getPrefix();
            if (StringUtils.isNotBlank(prefix)) {
                return prefix + String.format(format, generated);
            }
            return String.format(format, generated);
        }
        return generated;
    }

    private String getPrefix() {
        return IdPrefix.getPrefix(this.getSegmentValue());
        /*
        System.out.println("getSegmentValue = " + this.getSegmentValue());
        System.out.println("getSegmentColumnName = " + this.getSegmentColumnName());
        System.out.println("getTableName = " + this.getTableName());
        System.out.println("getValueColumnName = " + this.getValueColumnName());
        System.out.println("getSegmentColumnName = " + this.getSegmentColumnName());
        */
    }

    private String getCustomerFormat() {
        return IdPrefix.getFormat(this.getSegmentValue());
    }
}