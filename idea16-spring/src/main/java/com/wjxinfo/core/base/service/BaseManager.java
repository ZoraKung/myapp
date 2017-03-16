package com.wjxinfo.core.base.service;

import com.wjxinfo.core.base.dao.BaseDao;
import com.wjxinfo.core.base.model.BaseEntity;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.persistence.DynamicSpecifications;
import com.wjxinfo.core.base.vo.LabelValue;
import com.wjxinfo.core.base.vo.Principal;
import com.wjxinfo.core.base.web.utils.SearchFilter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Base Service
 */
public class BaseManager<E> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @PersistenceContext
    protected EntityManager em;
    @Autowired
    private BaseDao<E> dao;

    public List<Class<?>> getSQLQueryList(String sql, Class<?> resultClass) {
        return em.unwrap(Session.class).createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(resultClass)).list();
    }

    public Integer getSQLQueryCount(String sql) {
        return (Integer) em.unwrap(Session.class).createSQLQuery(sql).uniqueResult();
    }

    public Principal getPrincipal() {
        try {
            Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Page<E> getEntityByPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType, String sortOrder, boolean isDistinct) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType, sortOrder);
        Specification<E> spec = buildSpecification(searchParams, isDistinct);
        return dao.findAll(spec, pageRequest);
    }

    public Page<E> getEntityByPage(Map<String, Object> searchParams,
                                   int pageNumber, int pageSize, String sortType, String sortOrder) {
        return this.getEntityByPage(searchParams, pageNumber, pageSize, sortType, sortOrder, false);
    }

    public Page<E> getEntityByPage(Map<String, Object> searchParams, Pageable pageRequest) {
        Specification<E> spec = buildSpecification(searchParams);
        return dao.findAll(spec, pageRequest);
    }

    public Page<E> getEntityByPage(Map<String, Object> searchParams, Pageable pageRequest, boolean isDistinct) {
        Specification<E> spec = buildSpecification(searchParams, isDistinct);
        return dao.findAll(spec, pageRequest);
    }

    public Page<E> getEntityByPage(Specification<E> spec, Pageable pageRequest) {
        return dao.findAll(spec, pageRequest);
    }

    public Page<E> getEntityByPage(Specification<E> spec, Specification<E> otherSpec, Pageable pageRequest) {
        return dao.findAll(Specifications.where(spec).and(otherSpec), pageRequest);
    }

    private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType, String sortOrder) {
        Sort sort = buildSort(sortType, sortOrder);
        return new PageRequest(pageNumber - 1, pagzSize, sort);
    }

    private Sort buildSort(String sortType, String sortOrder) {
        Sort sort = null;
        if (StringUtils.isNotEmpty(sortType)) {
            if ("auto".equals(sortType)) {
                sort = new Sort(Sort.Direction.DESC, "id");
            } else if ("asc".equals(sortOrder)) {
                sort = new Sort(Sort.Direction.ASC, sortType);
            } else {
                sort = new Sort(Sort.Direction.DESC, sortType);
            }
        } else {
            sort = new Sort(Sort.Direction.ASC, "id");
        }
        return sort;
    }

    public Specification<E> buildSpecification(Map<String, Object> searchParams) {
        return buildSpecification(searchParams, false);
    }

    public Specification<E> buildSpecification(Map<String, Object> searchParams, boolean isDistinct) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Class<E> entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Specification<E> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass, isDistinct);
        return spec;
    }

    public Long getCountBySpec(Specification<E> spec) {
        return dao.count(spec);
    }

    public Long getCountBySpec(Specification<E> spec, Specification<E> otherSpec) {
        return dao.count(Specifications.where(spec).and(otherSpec));
    }

    public Long getCountBySpec(Map<String, Object> searchParams) {
        return dao.count(buildSpecification(searchParams));
    }

    public Long getCountBySpec(Map<String, Object> searchParams, boolean isDistinct) {
        return dao.count(buildSpecification(searchParams, isDistinct));
    }

    public E save(E entity) {
        dao.saveAndFlush(entity);
        return entity;
    }

    public List<E> save(List<E> entities) {
        return dao.save(entities);
    }

    public void remove(E entity) {
        dao.delete(entity);
    }

    public void removeInBatch(Iterable<E> entities) {
        dao.deleteInBatch(entities);
    }

    public void remove(String id) {
        dao.delete(id);
    }

    public Long getCountByAll() {
        return dao.count();
    }

    public E getEntityById(String id) {
        E entity = dao.findOne(id);
        return entity;
        //return getSaveState(entity);
    }

    /*
    private E getSaveState(E entity) {
        if (entity != null && entity instanceof BaseEntity) {
            HibernateUtils.initializeObjectLazy(entity);
            ((BaseEntity) entity).setSavedState(SerializationUtils.clone((BaseEntity) entity));
        }
        return entity;
    }
    */
    public List<E> getAll() {
        return dao.findAll();
    }

    public List<E> getAllBySort(String sortType, String sortOrder) {
        Sort sort = buildSort(sortType, sortOrder);
        return dao.findAll(sort);
    }

    public List<E> getEntityBySpec(Specification<E> spec) {
        return dao.findAll(spec);
    }

    public List<E> getEntityBySpec(Specification<E> spec, Specification<E> otherSpec) {
        return dao.findAll(Specifications.where(spec).and(otherSpec));
    }

    public List<E> getEntityBySpec(Map<String, Object> searchParams) {
        return dao.findAll(buildSpecification(searchParams));
    }

    public List<E> getEntityBySpec(Map<String, Object> searchParams, boolean isDistinct) {
        return dao.findAll(buildSpecification(searchParams, isDistinct));
    }

    public List<E> getEntityBySpecAndOrder(Map<String, Object> searchParams, String sortType, String sortOrder) {
        Sort sort = buildSort(sortType, sortOrder);
        Specification<E> spec = buildSpecification(searchParams);
        return dao.findAll(spec, sort);
    }

    public E getOneBySpec(Map<String, Object> searchParams) {
        List<E> list = getEntityBySpec(searchParams);
        if (list.size() > 0) {
            return list.get(0);
            //return getSaveState(list.get(0));
        } else {
            return null;
        }
    }

    public List<LabelValue> getLabelValueList(Map<String, Object> searchParams, String labelProperty, String valueProperty) {
        List<LabelValue> result = new ArrayList<LabelValue>();
        try {
            List<E> list = null;
            if (searchParams != null) {
                list = getEntityBySpec(searchParams);
            } else {
                list = getAll();
            }
            Set<String> labels = new HashSet<String>();
            for (E e : list) {
                LabelValue labelValue = new LabelValue();
                Object label = PropertyUtils.getProperty(e, labelProperty);
                if (label != null) {
                    labelValue.setLabel(label.toString());
                } else {
                    labelValue.setLabel("");
                }
                Object value = PropertyUtils.getProperty(e, valueProperty);
                if (value != null) {
                    labelValue.setValue(value.toString());
                } else {
                    labelValue.setValue("");
                }
                if (labels.add(label.toString())) {
                    result.add(labelValue);
                }

            }
        } catch (Exception ex) {
            logger.error(String.format("Get label value list error: %s", ex.getMessage()));
        }
        return result;
    }

    public List<LabelValue> getLabelValueList(String labelProperty, String valueProperty) {
        return getLabelValueList(null, labelProperty, valueProperty);
    }

    public boolean isExist(E obj, String... properties) {
        boolean result = false;
        Map<String, Object> searchMap = new HashMap<String, Object>();
        for (String property : properties) {
            try {
                Object value = PropertyUtils.getProperty(obj, property);
                if (value != null) {
                    searchMap.put("EQ_" + property, value);
                }
            } catch (Exception ex) {
            }
        }
        if (searchMap.size() > 0) {
            List<E> objects = getEntityBySpec(searchMap);
            if (objects.size() > 0) {
                if (obj instanceof BaseEntity && ((BaseEntity) obj).getId() == null) {
                    result = true;
                } else if (obj instanceof BaseEntity && ((BaseEntity) obj).getId() != null) {
                    for (E object : objects) {
                        if (object instanceof BaseEntity && ((BaseEntity) object).getId() != null
                                && !((BaseEntity) object).equals((BaseEntity) obj)) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public String getPropertyAndValue(E obj, String... properties) {
        StringBuilder builder = new StringBuilder();
        //String result = "";
        for (String property : properties) {
            try {
                Object value = PropertyUtils.getProperty(obj, property);
                if (value != null) {
                    if (StringUtils.isNotEmpty(builder.toString())) {
                        //result += ",";
                        builder.append(",");
                    }
                    //result += property + ":" + value.toString();
                    //result +=  value.toString();
                    builder.append(value.toString());
                }
            } catch (Exception ex) {
            }
        }
        return builder.toString();
        //return result;
    }

    public List<LabelValue> findLabelValueList(Map<String, Object> searchParams, String labelProperty, String term) {
        List<LabelValue> result = new ArrayList<LabelValue>();

        return result;
    }
}
