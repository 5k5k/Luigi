// ITalkAidlInterface.aidl
package com.morladim.mario.ipc;

import com.morladim.mario.ipc.ITalkCallbackAidlInterface;

// Declare any non-default types here with import statements

interface ITalkAidlInterface {

    void tellServer(String s);

    String getMessage();

    void registerCallback(ITalkCallbackAidlInterface callback);

    void unRegisterCallback(ITalkCallbackAidlInterface callback);
}