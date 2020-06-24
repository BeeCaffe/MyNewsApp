package com.example.newsappbykotlin.utils;

import java.io.IOException;

public interface HttpCallbackListener {
    String onFinish(String response) throws IOException;

    void onError(Exception e);
}
