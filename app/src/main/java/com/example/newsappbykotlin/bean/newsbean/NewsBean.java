package com.example.newsappbykotlin.bean.newsbean;

import com.example.newsappbykotlin.bean.Bean;

import java.io.Serializable;

public class NewsBean  extends Bean implements Serializable {
        private int status;
        private String msg;
        private Result result;
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

        public void setResult(Result result) {
            this.result = result;
        }
        public Result getResult() {
            return result;
        }
}
