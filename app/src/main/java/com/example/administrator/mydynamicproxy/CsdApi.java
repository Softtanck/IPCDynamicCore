package com.example.administrator.mydynamicproxy;

import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.mydynamicproxy.annotation.RemoteClass;
import com.example.administrator.mydynamicproxy.annotation.RemoteMethod;
import com.example.administrator.mydynamicproxy.req.CsdRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CsdApi {

    private static final String TAG = "Tanck";

    public static <T> T getInterface(final Class<?> clazz) {
        Object proxyInstance = Proxy.newProxyInstance(CsdApi.class.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String localInvokedMethodName = method.getName();
                Log.d(TAG, "Invoke local method name : " + localInvokedMethodName);
                Annotation[] clazzDeclaredAnnotations = clazz.getDeclaredAnnotations();
                String remoteClassName = null;
                // 1. Get remote class name
                for (Annotation clazzDeclaredAnnotation : clazzDeclaredAnnotations) {
                    Annotation remoteClassAnnotation = clazz.getAnnotation(clazzDeclaredAnnotation.annotationType());
                    Log.d(TAG, "Remote clazz type : " + clazzDeclaredAnnotation.annotationType());
                    if (remoteClassAnnotation != null && remoteClassAnnotation instanceof RemoteClass) {
                        RemoteClass remoteClass = (RemoteClass) remoteClassAnnotation;
                        remoteClassName = remoteClass.remoteClassName();
                        Log.d(TAG, "Remote class name : " + remoteClassName);
                        break;
                    }
                }
                // 2. Check remote class name is null ? if it's null then the proxy return null;
                if (TextUtils.isEmpty(remoteClassName)) {
                    Log.e(TAG, "ClassName is null. Is failed to invoke remote method");
                    return null;
                }
                // 3. Put the remote class name to CsdRequest
                final CsdRequest csdRequest = new CsdRequest();
                csdRequest.setRemoteClassName(remoteClassName);
                // 4. Get remote method name, callbackOnMainThread, syncInvokeRemoteMethod
                Annotation[] methodDeclaredAnnotations = method.getDeclaredAnnotations();
                for (Annotation methodDeclaredAnnotation : methodDeclaredAnnotations) {
                    Log.d(TAG, "Remote method type : " + methodDeclaredAnnotation.annotationType());
                    Annotation remoteMethodAnnotation = method.getAnnotation(methodDeclaredAnnotation.annotationType());
                    if (remoteMethodAnnotation != null && remoteMethodAnnotation instanceof RemoteMethod) {
                        RemoteMethod remoteMethod = (RemoteMethod) remoteMethodAnnotation;
                        String remoteMethodName = remoteMethod.remoteMethodName();
                        Log.d(TAG, "Remote method name : " + remoteMethodName);
                        boolean callbackOnMainThread = remoteMethod.callbackOnMainThread();
                        Log.d(TAG, "Callback on main thread : " + callbackOnMainThread);
                        boolean syncInvokeRemoteMethod = remoteMethod.syncInvokeRemoteMethod();
                        Log.d(TAG, "Sync invoke remote method  : " + syncInvokeRemoteMethod);
                        csdRequest.setCallbackOnMainThread(callbackOnMainThread);
                        csdRequest.setSyncInvokeMotehod(syncInvokeRemoteMethod);
                    }
                }
                // 5. Get method return value Type
                Class<?> returnType = method.getReturnType();
                Log.d(TAG, "Invoke method return type : " + returnType.getName());
                // 6. Get method request args
                if (args != null && args.length > 0) {
                    csdRequest.setRequestArgs(args);
                    for (Object arg : args) {
                        Log.d(TAG, "Invoke method args : " + arg);
                    }
                } else {
                    Log.d(TAG, "Invoke method args : " + null);
                }

                Log.d(TAG, "IsPrimitive Type : " + returnType.isPrimitive());

                // 7. Do some inits
                if (csdRequest.isSyncInvokeMotehod()) {
                    // TODO : Invoke remote method and return
                    return returnType.getName().equalsIgnoreCase(Void.class.getSimpleName()) ? null : makeReturnValue(returnType, null);//Class.forName( returnType.getClass().getName()).newInstance();//returnType.getClass().newInstance();//Class.forName(returnType.getClass()).newInstance();
                } else {
                    return returnType.getName().equalsIgnoreCase(Void.class.getSimpleName()) ? null : makeReturnValue(returnType, null);
                }
            }
        });
        return (T) proxyInstance;
    }


    private static <T> T makeReturnValue(Class<?> clazz, Object obj) {
        Object tempObj = null;
        if (clazz.isPrimitive()) {
            switch (clazz.getName()) {
                case "int":
                    if (obj == null) {
                        tempObj = 0;
                    } else {
                        tempObj = int.class.cast(obj);
                    }
                    break;
                case "long":
                    if (obj == null) {
                        tempObj = 0;
                    } else {
                        tempObj = long.class.cast(obj);
                    }
                    break;
                case "double":
                    if (obj == null) {
                        tempObj = 0;
                    } else {
                        tempObj = double.class.cast(obj);
                    }
                    break;
                case "short":
                    if (obj == null) {
                        tempObj = 0;
                    } else {
                        tempObj = short.class.cast(obj);
                    }
                    break;
                case "float":
                    if (obj == null) {
                        tempObj = 0;
                    } else {
                        tempObj = float.class.cast(obj);
                    }
                    break;
                case "boolean":
                    if (obj == null) {
                        tempObj = false;
                    } else {
                        tempObj = boolean.class.cast(obj);
                    }
                    break;
                case "byte":
                    if (obj == null) {
                        tempObj = false;
                    } else {
                        tempObj = byte.class.cast(obj);
                    }
                    break;
                // TODO : Custom rsp should be checked and json
            }
        }
        return (T) tempObj;
    }
}
