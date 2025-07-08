package com.sise.mishabitos.shared;

public interface Callback <T>{
    void onSuccess(T result);
    void onFailure();
}