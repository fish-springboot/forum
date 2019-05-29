package com.github.fish56.forum.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来决定一个参数是否需要被校验
 * 我们的ValidatorAop只校验需要被@ShouldValidate标注的参数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ShouldValidate {
    Class<?>[] value() default {};

    /**
     * 没什么具体作用，就用用来做validate的分组
     *
     * 我们的设计中，创建实体是有些字段不能为空，
     * 但是修改的时候可以是空的，所以需要做分组
     * 下面这个没什么具体作用，就用用来做validate的分组
     */
    public static interface OnCreate{}
    public static interface OnUpdate{}
}