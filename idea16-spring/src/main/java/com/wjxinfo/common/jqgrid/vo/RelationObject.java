package com.wjxinfo.common.jqgrid.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jack on 14-9-16.
 */
public class RelationObject {
    //Relation property
    private String relationProperty;
    //Relation Entity
    private Class<? extends Serializable> entityClass;
    //Relation Entity Properties;
    private List<String> properties;

    public String getRelationProperty() {
        return relationProperty;
    }

    public void setRelationProperty(String relationProperty) {
        this.relationProperty = relationProperty;
    }

    public Class<? extends Serializable> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<? extends Serializable> entityClass) {
        this.entityClass = entityClass;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}
