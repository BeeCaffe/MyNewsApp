package com.example.newsappbykotlin.bean.searchbean;

import java.util.List;

public class SearchResult {
        private String keyword;
        private String num;
        private List<SearchList> list;
        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
        public String getKeyword() {
            return keyword;
        }

        public void setNum(String num) {
            this.num = num;
        }
        public String getNum() {
            return num;
        }

        public void setList(List<SearchList> list) {
            this.list = list;
        }
        public List<SearchList> getList() {
            return list;
        }
}
