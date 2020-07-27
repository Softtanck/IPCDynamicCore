package com.example.administrator.mydynamicproxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RemoteMethod {
    String remoteMethodName(); // 远程函数名字

    boolean syncInvokeRemoteMethod() default true; // 是否同步调用远程函数,默认为true

    boolean callbackOnMainThread() default false; //  默认回调不在主线程,前提是设置了有回调
}
