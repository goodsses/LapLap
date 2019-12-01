package com.sh.common.anno;

import java.lang.annotation.*;

/**
 * @author pch 651158394@qq.com
 * @version 2018/8/30 9:04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Excel {

    String[] header() default {};
    String sheetName() default "sheet1";
}
