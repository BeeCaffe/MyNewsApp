package com.example.newsappbykotlin.utils;

import com.example.newsappbykotlin.bean.Bean;

import java.net.URL;
import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpCallableUtils implements Callable<Bean> {
    private String address;
    private int Tag;

    public HttpCallableUtils(String address, int tag) {
        this.address = address;
        Tag = tag;
    }

    @Override
    public Bean call() throws Exception {
        URL url = new URL(address);
        OkHttpClient client = new OkHttpClient();
        //创建request对象并且绑定url
        Request request = new Request.Builder().url(url).build();
        //发送request
        Response response = client.newCall(request).execute();
        //获取response String
        String responseString= response.body().string();
        Bean bean=null;
        switch (Tag){
            case Bean.CAHNNELBEAN:
                MyJsonParser jsonParser = new MyJsonParser();
                bean =jsonParser.parse2channelBean(responseString);
                break;
            case Bean.NEWSBEAN:
                MyJsonParser jsonParser1 = new MyJsonParser();
                bean = jsonParser1.parse2newsBean(responseString);
                break;
            case Bean.SEARCHBEAN:
                MyJsonParser jsonParser2 = new MyJsonParser();
                bean = jsonParser2.parse2searchlBean(responseString);
                break;
            default:break;
        }
        return bean;
    }
}
