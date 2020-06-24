package com.example.newsappbykotlin.utils;

import com.example.newsappbykotlin.bean.channelbean.ChannelBean;
import com.example.newsappbykotlin.bean.newsbean.NewsBean;
import com.example.newsappbykotlin.bean.newsbean.NewsList;
import com.example.newsappbykotlin.bean.searchbean.SearchBean;
import com.example.newsappbykotlin.bean.searchbean.SearchList;
import com.example.newsappbykotlin.bean.Bean;
import com.google.gson.Gson;

import java.util.List;

public class MyJsonParser {
    public String testString = "{\n" +
            "    \"status\": 0,\n" +
            "    \"msg\": \"ok\",\n" +
            "    \"result\": {\n" +
            "        \"channel\": \"头条\",\n" +
            "        \"num\": \"10\",\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"title\": \"中国开闸放水27天解救越南旱灾\",\n" +
            "                \"time\": \"2016-03-16 07:23\",\n" +
            "                \"src\": \"中国网\",\n" +
            "                \"category\": \"mil\",\n" +
            "                \"pic\": \"http://api.jisuapi.com/news/upload/20160316/105123_31442.jpg\",\n" +
            "                \"content\": \"<p class=\\\"art_t\\\">　　原标题：防总：应越南请求 中方启动澜沧江水电站水量应急调度</p><p class=\\\"art_t\\\">　　记者从国家防总获悉，应越南社会主义共和国请求，我方启动澜沧江梯级水电站水量应急调度，缓解湄公河流域严重旱情。3月15日8时，澜沧江景洪水电站下泄流量已加大至2190立方米每秒，标志着应越方请求，由我方实施的澜沧江梯级水电站水量应急调度正式启动。</p>\",\n" +
            "                \"url\": \"http://mil.sina.cn/zgjq/2016-03-16/detail-ifxqhmve9235380.d.html?vt=4&pos=108\",\n" +
            "                \"weburl\": \"http://mil.news.sina.com.cn/china/2016-03-16/doc-ifxqhmve9235380.shtml\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}\n";

    static final String TAG = "MyJson Parser Log: ";

    public Bean parse2newsBean(String jsonStr){
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(jsonStr,NewsBean.class);
        return newsBean;
    }

    public Bean parse2channelBean(String jsonStr){
        Gson gson = new Gson();
        ChannelBean channelBean = gson.fromJson(jsonStr,ChannelBean.class);
        return channelBean;
    }

    public Bean parse2searchlBean(String jsonStr){
        Gson gson = new Gson();
        SearchBean searchBean = gson.fromJson(jsonStr,SearchBean.class);
        return searchBean;
    }



    public static void showNewsBean( NewsBean newsBean){
        StringBuilder msg = new StringBuilder(newsBean.getStatus() + " " + newsBean.getMsg() + " " + newsBean.getResult() + " ");

        List<NewsList> newsLists = newsBean.getResult().getList();
        for(int i=0;i<newsLists.size();++i){
            msg.append( " "+newsLists.get(i).getContent());
        }
        MyLog.e(TAG, msg.toString());
    }

    public static void showChannelBean(ChannelBean bean){
        StringBuilder msg = new StringBuilder(bean.getMsg()+ " " + bean.getStatus()+ " ");
        List<String> list = bean.getResult();
        for(String item:list){
            msg.append(item+" ");
        }
        MyLog.e(TAG,msg.toString());
    }

    public static void showSearchBean(SearchBean bean){
        StringBuilder msg = new StringBuilder(bean.getMsg()+ " " + bean.getStatus()+ " "+bean.getResult().getKeyword());
        List<SearchList> newsLists = bean.getResult().getList();
        for(int i=0;i<newsLists.size();++i){
            msg.append( " "+newsLists.get(i).getContent());
        }
        MyLog.e(TAG, msg.toString());
    }

}
