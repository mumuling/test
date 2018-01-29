package com.zhongtie.work.util.parse;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * 参数自动解析KEY
 * <pre>
 * <code>
 *   &#64;Parse(value="name')
 *   String mName;
 *  &#64;Parse(value="ID')
 *   int id;
 * </code></pre>
 *
 * @author: Chaek
 * @date: 2018/1/25
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface BindKey {
    /**
     * value 默认为参数名称
     */
    String value() default "";
}