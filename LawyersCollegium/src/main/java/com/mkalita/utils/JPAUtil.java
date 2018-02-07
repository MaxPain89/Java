package com.mkalita.utils;

import com.mkalita.jpa.ConfigurableFetchMode;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class JPAUtil {

    public static synchronized <T> Optional<T> getObject(
            final EntityManager em,
            final String idField,
            final Object idValue,
            final Class<T> aClass) {
        try {
            em.clear();
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<T> query = cb.createQuery(aClass);
            Root<T> table = query.from(aClass);

            applyFetchMode(aClass, table, query);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(table.get(idField), idValue));


            return Optional.of(em.createQuery(query.select(table)
                    .where(cb.and(
                            predicates.toArray(new Predicate[predicates.size()]))))
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Unknown error", e);
        }
    }

    public static <T> List<T> getObjects(
            final EntityManager em,
            final JPASortParams sortParams,
            final Class<T> aClass) {
        return getObjects(em, Collections.emptyMap(), sortParams, aClass);
    }

    @SuppressWarnings("WeakerAccess")
    public static synchronized <T> List<T> getObjects(
            final EntityManager em,
            final Map<String, Object> conditions,
            final JPASortParams sortParams,
            final Class<T> aClass) {
        try {
            em.clear();
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<T> query = cb.createQuery(aClass);
            Root<T> table = query.from(aClass);

            applyFetchMode(aClass, table, query);

            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> condition : conditions.entrySet()) {
                String idField = condition.getKey();
                Object idValue = condition.getValue();
                Path<Object> objectPath = null;
                String lastName = "";
                for (String s : idField.split("\\.")) {
                    if (objectPath == null) {
                        objectPath = table.get(s);
                    } else {
                        objectPath = objectPath.get(s);
                    }
                    lastName = s;
                }
                if (idValue instanceof Collection) {
                    if (((Collection) idValue).isEmpty()) {
                        return new ArrayList<>();
                    }
                    if (objectPath != null) {
                        predicates.add(objectPath.in((Collection) idValue));
                    }
                } else if (idValue instanceof DateRange) {
                    DateRange dr = (DateRange) idValue;
                    if (dr.getStartDate() != null) {
                        Path<Date> pd = objectPath.getParentPath().get(lastName);
                        if (dr.isStartIncluded()) {
                            predicates.add(cb.greaterThanOrEqualTo(pd, dr.getStartDate()));
                        } else {
                            predicates.add(cb.greaterThan(pd, dr.getStartDate()));
                        }
                    }
                    if (dr.getEndDate() != null) {
                        Path<Date> pd = objectPath.getParentPath().get(lastName);
                        if (dr.isEndIncluded()) {
                            predicates.add(cb.lessThanOrEqualTo(pd, dr.getEndDate()));
                        } else {
                            predicates.add(cb.lessThan(pd, dr.getEndDate()));
                        }
                    }
                }  else {
                    predicates.add(cb.equal(objectPath, idValue));
                }
            }

            query = query.select(table)
                    .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

            applySortParams(sortParams, cb, query, table);

            TypedQuery<T> objectQuery = em.createQuery(query);

            if (sortParams != null) {
                final int maxResult = sortParams.getMaxResult();
                final int offset = sortParams.getOffset();
                if (maxResult > 0) {
                    objectQuery = objectQuery.setMaxResults(maxResult);
                }
                if (offset > 0) {
                    objectQuery = objectQuery.setFirstResult(offset);
                }
            }

            return objectQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Unknown error + %s", e.toString()));
        }
    }

    private static <T> void applyFetchMode(Class<T> aClass, Root<T> table, CriteriaQuery<T> query) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (ConfigurableFetchMode.class.isAssignableFrom(aClass)) {
            query.distinct(true);
            Method m = aClass.getMethod(ConfigurableFetchMode.methodName, FetchParent.class);
            m.invoke(null, table);
        }
    }

    private static <T> void applySortParams(JPASortParams sortParams, CriteriaBuilder cb, CriteriaQuery<T> query, Root<T> table) {
        if (sortParams != null) {
            final String orderField = sortParams.getOrderField();
            final boolean ascending = sortParams.isAscending();
            if (orderField != null) {
                Path<Object> expression = null;
                for (String s : orderField.split("\\.")) {
                    if (expression == null) {
                        expression = table.get(s);
                    } else {
                        expression = expression.get(s);
                    }
                }
                query.orderBy(ascending ? cb.asc(expression) : cb.desc(expression));
            }
        }
    }
}
