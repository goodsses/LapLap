package com.sh.common.service;

import javax.persistence.criteria.Predicate;

public interface Criteriable<T> {

    Predicate toPredicate(T t);
}
