package com.cookandroid.flowerdesign.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Upload {
    @SerializedName("id") //JSON으로 serialize 될 때 매칭되는 이름을 명시하는 목적으로 사용되는 어노테이션
    @Expose //object 중 해당 값이 null일 경우, json으로 만들 필드를 자동 생략해 준다.
    private int id;

    public Upload() {
    }

    public Upload(int id, String title, String content, String files) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.files = files;
    }

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("files")
    @Expose
    private String files;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
