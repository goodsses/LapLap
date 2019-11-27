package com.sh.common.anno;

import com.sh.common.enu.WhereType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CriteriaWhere {

    WhereType type() default WhereType.LIKE;

    String linkChar() default "-";
}
