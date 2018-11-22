package com.umeng.soexample.services;

public interface HttpListener {
    void success(String data);

    void fail(String error);
}
