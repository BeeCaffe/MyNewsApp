package com.example.newsappbykotlin.bean.newsbean;

import java.util.List;

public class Result {
        private String channel;
        private String num;
        private List<NewsList> list;
        public void setChannel(String channel) {
            this.channel = channel;
        }
        public String getChannel() {
            return channel;
        }

        public void setNum(String num) {
            this.num = num;
        }
        public String getNum() {
            return num;
        }

        public void setList(List<NewsList> list) {
            this.list = list;
        }
        public List<NewsList> getList() {
            return list;
        }
}
