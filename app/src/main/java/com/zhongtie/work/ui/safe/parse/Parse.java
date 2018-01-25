package com.zhongtie.work.ui.safe.parse;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: Chaek
 * @date: 2018/1/25
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Parse {
    String key() default "";
}