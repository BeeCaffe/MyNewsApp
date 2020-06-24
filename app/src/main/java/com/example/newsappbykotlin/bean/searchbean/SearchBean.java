package com.example.newsappbykotlin.bean.searchbean;

import com.example.newsappbykotlin.bean.Bean;

import java.io.Serializable;

public class SearchBean extends Bean implements Serializable {
        private int status;
        private String msg;
        private SearchResult result;
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

        public void setResult(SearchResult result) {
            this.result = result;
        }
        public SearchResult getResult() {
            return result;
        }
}
