package com.example.administrator.mydynamicproxy;

import com.example.administrator.mydynamicproxy.annotation.RemoteClass;
import com.example.administrator.mydynamicproxy.annotation.RemoteMethod;

@RemoteClass(remoteClassName = "com.remote.package.xxxx")
public interface IDealerRemoteInterface {
    @RemoteMethod(remoteMethodName = "getDealerState")
    boolean getDealerState();

    @RemoteMethod(remoteMethodName = "verifyDealerState")
    boolean verifyDealerState(String userInputValue);
}
