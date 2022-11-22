package com.cookandroid.flowerdesign.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchRank {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("cnt")
    @Expose
    private int cnt;

    @SerializedName("keyword")
    @Expose
    private String keyword;

    public SearchRank(int id, int cnt, String keyword) {
        this.id = id;
        this.cnt = cnt;
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
