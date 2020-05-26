package com.aop.handler;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReqLog {

    String value() default "";

    String desc() default "";

}
