package com.wjxinfo.core.base.utils.persistence;

import com.wjxinfo.core.base.web.utils.SearchFilter;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Parse search parameters
 */
public class DynamicSpecifications {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSpecifications.class);

    /**
     * Get Jpa Specification
     *
     * @param filters : Search Filter Collection
     * @param clazz   : Object Class
     * @param <T>     : Entity
     * @return : Specification
     */
    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz, final boolean isDistinct) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (filters != null && !(filters.isEmpty())) {
                    query.distinct(isDistinct);
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    for (SearchFilter filter : filters) {
                        List<Predicate> subpredicates = new ArrayList<Predicate>();
                        Path expression = null;
                        String[] orNames = StringUtils.split(filter.fieldName, "|");
                        for (String orName : orNames) {
                            String[] tableNames = StringUtils.split(orName, ".");
                            if (tableNames.length > 1) {
                                Join[] joins = new Join[tableNames.length - 1];
                                joins[0] = root.join(tableNames[0]);
                                for (int i = 1; i < tableNames.length - 1; i++) {
                                    joins[i] = joins[i - 1].join(tableNames[i]);
                                }
                                expression = joins[tableNames.length - 2].get(tableNames[tableNames.length - 1]);
                            } else {
                                expression = root.get(tableNames[tableNames.length - 1]);
                            }
                            filter.value = StringEscapeUtils.escapeSql(filter.value.toString());
                            // logic operator
                            switch (filter.operator) {
                                case EQ:
                                    subpredicates.add(builder.equal(expression, (Comparable) filter.value));
                                    break;
                                case NE:
                                    subpredicates.add(builder.notEqual(expression, (Comparable) filter.value));
                                    break;
                                case GT:
                                    subpredicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                    break;
                                case LT:
                                    subpredicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                    break;
                                case GE:
                                    subpredicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                    break;
                                case LE:
                                    subpredicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                    break;
                                case BW:
                                    subpredicates.add(builder.like(expression, filter.value + "%"));
                                    break;
                                case BN:
                                    subpredicates.add(builder.notLike(expression, filter.value + "%"));
                                    break;
                                case EW:
                                    subpredicates.add(builder.like(expression, "%" + filter.value));
                                    break;
                                case EN:
                                    subpredicates.add(builder.notLike(expression, "%" + filter.value));
                                    break;
                                case CN:
                                    subpredicates.add(builder.like(expression, "%" + filter.value + "%"));
                                    break;
                                case NC:
                                    subpredicates.add(builder.notLike(expression, "%" + filter.value + "%"));
                                    break;
                                case NI:
                                    if (filter.value instanceof List) {
                                        Predicate where = builder.disjunction();
                                        for (String obj : (List<String>) filter.value) {
                                            where = builder.or(where, builder.equal(expression, obj));
                                        }
                                        subpredicates.add(where.not());
                                    }
                                    break;
                                case IN:
                                    if (filter.value instanceof List) {
                                        Predicate where = builder.disjunction();
                                        for (String obj : (List<String>) filter.value) {
                                            where = builder.or(where, builder.equal(expression, obj));
                                        }
                                        subpredicates.add(where);
                                    }
                                    break;
                            }
                        }
                        if (subpredicates.size() > 0) {
                            predicates.add(builder.or(subpredicates.toArray(new Predicate[subpredicates.size()])));
                        }
                    }
                    // And all conditions
                    if (predicates.size() > 0) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }
                return builder.conjunction();
            }
        };
    }

    /**
     * Get Jpa Specification
     *
     * @param filters : Search Filter Collection
     * @param clazz   : Object Class
     * @param <T>     : Entity
     * @return : Specification
     */
    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz) {
        return bySearchFilter(filters, clazz, false);
    }
}
