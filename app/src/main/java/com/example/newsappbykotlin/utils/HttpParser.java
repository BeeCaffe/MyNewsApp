package com.example.newsappbykotlin.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpParser {
    //news
    public static final String APPKEY = "85966d3aec88f31c";// 你的appkey
    public static final String URL = "https://api.jisuapi.com/news/get";
    public String channel = "头条";// utf8  新闻频道(头条,财经,体育,娱乐,军事,教育,科技,NBA,股票,星座,女性,健康,育儿)
    public int num = 10;// 数量 默认10，最大40

    //channels
    public static final String channelURL = "https://api.jisuapi.com/news/channel";

    //search
    public static final String searchURL = "https://api.jisuapi.com/news/search";

    public String getNewsURL(){
        String url = null;
        try {
            url = URL + "?channel=" + URLEncoder.encode(this.channel, "utf-8") + "&num=" + this.num + "&appkey=" + APPKEY;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String getNewsURL(String channel, int num){
        this.channel = channel;
        this.num = num;
        String url = null;
        try {
            url = URL + "?channel=" + URLEncoder.encode(this.channel, "utf-8") + "&num=" + this.num + "&appkey=" + APPKEY;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String getChannelURL(){
        String url = channelURL + "?appkey=" + APPKEY;
        return url;
    }

    public String getSearchURL(String keyword){
        String url = null;
        try {
            url = searchURL + "?keyword=" + URLEncoder.encode(keyword, "utf-8") + "&appkey=" + APPKEY;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}
