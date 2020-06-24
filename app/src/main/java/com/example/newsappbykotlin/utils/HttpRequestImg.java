package com.example.newsappbykotlin.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpRequestImg implements Callable<Bitmap> {
    private String httpAddress;

    public HttpRequestImg(String httpAddress) {
        this.httpAddress = httpAddress;
    }

    @Override
    public Bitmap call() throws Exception {
        Bitmap myBitmap = null;
        try {
            URL url = new URL(httpAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            FileInputStream fis = null;
            try {
                fis = new FileInputStream("/Users/leiqinghua/AndroidStudioProjects/NewsAppByKotlin/app/src/main/res/drawable-v24/girl.jpeg");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            myBitmap  = BitmapFactory.decodeStream(fis);
            return myBitmap;
        }
        return myBitmap;
    }
}
