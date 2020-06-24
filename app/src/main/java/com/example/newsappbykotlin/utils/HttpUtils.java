package com.example.newsappbykotlin.utils;

import android.os.Handler;
import android.os.Message;

import com.example.newsappbykotlin.bean.Bean;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils extends Thread{
    private final static String TAG = "HttpUtil Log: ";
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) throws MalformedURLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    OkHttpClient client = new OkHttpClient();
                    //创建request对象并且绑定url
                    Request request = new Request.Builder().url(url).build();
                    //发送request
                    Response response = client.newCall(request).execute();
                    //获取response String
                    String responseString= response.body().string();
                    if(listener!=null){
                        listener.onFinish(responseString);
                    }
                }catch (Exception e){
                    if(listener!=null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void httpHandlerGetBean(final Handler handler, final String address, final int Tag) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    OkHttpClient client = new OkHttpClient();
                    //创建request对象并且绑定url
                    Request request = new Request.Builder().url(url).build();
                    //发送request
                    Response response = client.newCall(request).execute();
                    //获取response String
                    String responseString= response.body().string();
                    Bean bean = null;
                    Message msg = new Message();
                    switch (Tag){
                        case Bean.CAHNNELBEAN:
                            MyJsonParser jsonParser = new MyJsonParser();
                            bean = jsonParser.parse2channelBean(responseString);
                            msg.obj = bean;
                            msg.what = Bean.CAHNNELBEAN;
                            break;
                        case Bean.NEWSBEAN:
                            MyJsonParser jsonParser1 = new MyJsonParser();
                            bean = jsonParser1.parse2newsBean(responseString);
                            msg.obj = bean;
                            msg.what = Bean.NEWSBEAN;
                            break;
                        case Bean.SEARCHBEAN:
                            MyJsonParser jsonParser2 = new MyJsonParser();
                            bean = jsonParser2.parse2searchlBean(responseString);
                            msg.obj = bean;
                            msg.what = Bean.SEARCHBEAN;
                            break;
                        default:break;
                    }
                    MyLog.e(TAG,"handler request success!");
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                    Bean bean = null;
                    Message msg = new Message();
                    switch (Tag){
                        case Bean.CAHNNELBEAN:
                            bean = Bean.getFakeChannelBean();
                            msg.what = Bean.CAHNNELBEAN;
                            msg.obj = bean;
                            break;
                        case Bean.NEWSBEAN:
                            bean = Bean.getFakeNewsBean();
                            msg.what = Bean.NEWSBEAN;
                            msg.obj = bean;
                            break;
                        case Bean.SEARCHBEAN:
                            bean = Bean.getFakeSearchBean();
                            msg.what = Bean.SEARCHBEAN;
                            msg.obj = bean;
                            break;
                        default:break;
                    }
                    handler.sendMessage(msg);
                    MyLog.e(TAG,"handler request error! bean is fake!");
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
