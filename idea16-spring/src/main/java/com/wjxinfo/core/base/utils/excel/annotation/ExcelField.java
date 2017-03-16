package com.wjxinfo.core.base.utils.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel field annotation
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    //Export field name
    String value() default "";

    //export field title
    String title() default "";

    //export field type (0 : import/export; 1 : export, 2 : import)
    int type() default 0;

    //cell format
    String format() default "";

    //field align(0：auto；1：left；2：center；3：right
    int align() default 0;

    //field sort
    int sort() default 0;

    //set dictionary type
    String masterType() default "";

    // multiple values fields
    boolean multiple() default false;

    //reflection type
    Class<?> fieldType() default Class.class;

    //reflection type
    String propertyName() default "";

    //field group
    int[] groups() default {};

    //field formula
    String formula() default "";  //sample : sum(Irow:Jrow) -->row : row number

    //field width
    int width() default 0;

    //field hidden
    boolean hidden() default false;
}
