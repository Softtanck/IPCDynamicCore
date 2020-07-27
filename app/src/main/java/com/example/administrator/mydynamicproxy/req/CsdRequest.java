package com.example.administrator.mydynamicproxy.req;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class CsdRequest implements Parcelable {

    private String remoteClassName; // 远程类名
    private String remoteMethodName;// 远程函数名
    private boolean isSyncInvokeMotehod = true; // 是否同步调用远程函数
    private boolean isCallbackOnMainThread = false; // 是否回调在主线程,前提是设置了回调
    private Object[] requestArgs; // 请求参数
    private String requestReturnType; //请求接口返回值类型

    public CsdRequest() {
    }

    protected CsdRequest(Parcel in) {
        remoteClassName = in.readString();
        remoteMethodName = in.readString();
        isSyncInvokeMotehod = in.readByte() != 0;
        isCallbackOnMainThread = in.readByte() != 0;
        Parcelable[] parcelables = in.readParcelableArray(Object.class.getClassLoader());
        if (parcelables != null) {
            requestArgs = Arrays.copyOf(parcelables, parcelables.length, Object[].class);
        }
        requestReturnType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(remoteClassName);
        dest.writeString(remoteMethodName);
        dest.writeByte((byte) (isSyncInvokeMotehod ? 1 : 0));
        dest.writeByte((byte) (isCallbackOnMainThread ? 1 : 0));
        dest.writeParcelableArray((Parcelable[]) requestArgs, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CsdRequest> CREATOR = new Creator<CsdRequest>() {
        @Override
        public CsdRequest createFromParcel(Parcel in) {
            return new CsdRequest(in);
        }

        @Override
        public CsdRequest[] newArray(int size) {
            return new CsdRequest[size];
        }
    };

    public String getRemoteClassName() {
        return remoteClassName;
    }

    public void setRemoteClassName(String remoteClassName) {
        this.remoteClassName = remoteClassName;
    }

    public String getRemoteMethodName() {
        return remoteMethodName;
    }

    public void setRemoteMethodName(String remoteMethodName) {
        this.remoteMethodName = remoteMethodName;
    }

    public boolean isSyncInvokeMotehod() {
        return isSyncInvokeMotehod;
    }

    public void setSyncInvokeMotehod(boolean syncInvokeMotehod) {
        isSyncInvokeMotehod = syncInvokeMotehod;
    }

    public boolean isCallbackOnMainThread() {
        return isCallbackOnMainThread;
    }

    public void setCallbackOnMainThread(boolean callbackOnMainThread) {
        isCallbackOnMainThread = callbackOnMainThread;
    }

    public Object[] getRequestArgs() {
        return requestArgs;
    }

    public void setRequestArgs(Object[] requestArgs) {
        this.requestArgs = requestArgs;
    }

    public String getRequestReturnType() {
        return requestReturnType;
    }

    public void setRequestReturnType(String requestReturnType) {
        this.requestReturnType = requestReturnType;
    }
}
