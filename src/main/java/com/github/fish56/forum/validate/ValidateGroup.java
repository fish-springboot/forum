package com.github.fish56.forum.validate;

public @interface ValidateGroup {
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
