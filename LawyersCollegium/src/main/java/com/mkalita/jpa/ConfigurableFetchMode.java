package com.mkalita.jpa;

import org.hibernate.query.criteria.internal.path.SingularAttributeJoin;

import javax.persistence.criteria.FetchParent;

public abstract class ConfigurableFetchMode {
    public static final String methodName = "addFetchTypes";
    static <K, V> void addFetchTypes(FetchParent<K, V> root) {};

    static <K, V> boolean checkBreakCondition(FetchParent<K,V> root, Class clazz){
        return (root.getClass().equals(SingularAttributeJoin.class)
                && clazz.equals(((SingularAttributeJoin) root).getPathSource().getJavaType()));
    }

}
