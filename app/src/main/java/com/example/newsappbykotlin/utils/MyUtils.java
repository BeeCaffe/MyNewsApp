package com.example.newsappbykotlin.utils;

import android.graphics.Bitmap;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyUtils {
    public static void write2file(String fileName, String data) throws IOException {
        try {
            Log.e("MyUtils", fileName);
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Bitmap httpGetImage(String httpAddress){
        HttpRequestImg httpNews = new HttpRequestImg(httpAddress);
        FutureTask<Bitmap> futureTaskNews=new FutureTask<>(httpNews);
        new Thread(futureTaskNews).start();
        Bitmap img = null;
        try {
            img = futureTaskNews.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static String eraseBracketsInString(String xmlData){
        xmlData = xmlData.replaceAll("<[^>]*>",""); //删除左du部
        return xmlData.trim();
    }

    public static void xmlParser(String xmlData) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xmlData));
        int eventType = parser.getEventType();
        String name = "";
        String content = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = parser.getName();
            switch (eventType) {
                // 开始解析某个结点
                case XmlPullParser.START_TAG: {
                    if ("name".equals(nodeName)) {
                        name = parser.nextText();
                    } else if ("version".equals(nodeName)) {
                        content = parser.nextText();
                    }
                    break;
                }
                // 完成解析某个结点
                case XmlPullParser.END_TAG: {
                    if ("app".equals(nodeName)) {
                        Log.e("MainActivity", "name is " + name);
                        Log.e("MainActivity", "version is " + content);
                    }
                    break;
                }
                default:
                    break;
            }
            eventType = parser.next();
        }
    }

    public static HashMap<String,String> newsParser(String xmlData){
        int lastIndex = -1;
        HashMap<String,String> ret = new HashMap<>();
        List<String> contents = new ArrayList<>();
        for(int i=0;i<xmlData.length();++i){
            if(xmlData.charAt(i)=='<' && lastIndex>=0){
                contents.add(xmlData.substring(lastIndex+1,i-1));
            }else if(xmlData.charAt(i) == '>'){
                lastIndex=i;
            }
        }
        return ret;
    }
}
