package com.example.administrator.mydynamicproxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 元注解,表示运行时注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 作用接口、类、枚举、注解
public @interface RemoteClass {
    String remoteClassName();
}
