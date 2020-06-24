package com.example.newsappbykotlin.bean.channelbean;

import com.example.newsappbykotlin.bean.Bean;

import java.io.Serializable;
import java.util.List;

public class ChannelBean extends Bean implements Serializable {

        private int status;
        private String msg;
        private List<String> result;
        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        public String getMsg() {
            return msg;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
        public List<String> getResult() {
            return result;
        }
}
