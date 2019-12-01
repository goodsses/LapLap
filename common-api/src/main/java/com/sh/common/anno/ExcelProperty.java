package com.sh.common.anno;

import java.lang.annotation.*;

/**
 * @author pch 651158394@qq.com
 * @version 2018/8/30 9:04
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelProperty {

    int index();

    int cellWidth() default 80;

    int rowHeight() default 60;

    boolean notNull() default false;

    /**
     * 0小数,1字符串,2整数
     *
     * @return int
     */
    int cellType() default -1;

    /**
     * header
     *
     * @return String[]
     */
    String[] value() default {""};

    int decimals() default 2;

    boolean percent() default false;

    String format() default "";
}
