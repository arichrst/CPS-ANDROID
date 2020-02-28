package com.choki.cps.Interfaces;

public interface IApiListener {
    void onStart();
    void onSuccess(String result , String message);
    void onFailure(String message);
    void onUnauthenticated();
    void onEnd();
}
