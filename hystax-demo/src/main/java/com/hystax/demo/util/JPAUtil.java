package com.hystax.demo.util;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

public class JPAUtil {

    public static <T> T getObject(
            final EntityManager em,
            final String idField,
            final Object idValue,
            final Class<T> aClass) {
        try {
            em.clear();
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<T> query = cb.createQuery(aClass);
            Root<T> table = query.from(aClass);


            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(cb.equal(table.get(idField), idValue));


            return em.createQuery(query.select(table)
                    .where(cb.and(
                            predicates.toArray(new Predicate[predicates.size()]))))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Unknown error", e);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static <T> List<T> getObjects(
            final EntityManager em,
            final Map<String, Object> conditions,
            final JPASortParams sortParams,
            final Class<T> aClass) {
        try {
            em.clear();
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<T> query = cb.createQuery(aClass);
            Root<T> table = query.from(aClass);


            List<Predicate> predicates = new ArrayList<Predicate>();
            for (Map.Entry<String, Object> condition : conditions.entrySet()) {
                String idField = condition.getKey();
                Object idValue = condition.getValue();
                Path<Object> objectPath = null;
                for (String s : idField.split("\\.")) {
                    if (objectPath == null) {
                        objectPath = table.get(s);
                    } else {
                        objectPath = objectPath.get(s);
                    }
                }
                if (idValue instanceof Collection) {
                    if (((Collection) idValue).isEmpty()) {
                        return new ArrayList<T>();
                    }
                    if (objectPath != null) {
                        predicates.add(objectPath.in((Collection) idValue));
                    }
                } else {
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
            throw new RuntimeException("Unknown error", e);
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
