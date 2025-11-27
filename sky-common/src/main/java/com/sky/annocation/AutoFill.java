package com.sky.annocation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解AutoFill，用于标识需要进行功能字段自动填充处理的方法
 * 注解@Target(ElementType.METHOD) 作用：指定这个注解可以用在什么地方
 * 注解@Retention(RetentionPolicy.RUNTIME) 作用：指定注解的保留策略（生命周期）
 * 这里不用class而是用@interface，它是 Java 中用于定义自定义注解的关键字
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    // 数据库操作类型：UPDATE INSERT（都写到枚举的operation type里了）
    OperationType value();
}
