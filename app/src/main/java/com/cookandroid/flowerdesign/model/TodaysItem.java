package com.cookandroid.flowerdesign.model;

public class TodaysItem {
    String flowNm;
    String fSctNm;
    String fEngNm;
    String flowLang;
    String fContent;
    String fUse;
    String fGrow;
    String fType;
    String imgUrl1;
    String imgUrl2;
    String imgUrl3;

    public String getFlowNm() {
        return flowNm;
    }

    public void setFlowNm(String flowNm) {
        this.flowNm = flowNm;
    }

    public String getfSctNm() {
        return fSctNm;
    }

    public void setfSctNm(String fSctNm) {
        this.fSctNm = fSctNm;
    }

    public String getfEngNm() {
        return fEngNm;
    }

    public void setfEngNm(String fEngNm) {
        this.fEngNm = fEngNm;
    }

    public String getFlowLang() {
        return flowLang;
    }

    public void setFlowLang(String flowLang) {
        this.flowLang = flowLang;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }

    public String getfUse() {
        return fUse;
    }

    public void setfUse(String fUse) {
        this.fUse = fUse;
    }

    public String getfGrow() {
        return fGrow;
    }

    public void setfGrow(String fGrow) {
        this.fGrow = fGrow;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public String getImgUrl3() {
        return imgUrl3;
    }

    public void setImgUrl3(String imgUrl3) {
        this.imgUrl3 = imgUrl3;
    }

    @Override
    public String toString() {
        return "TodaysItem{" +
                "flowNm='" + flowNm + '\'' +
                ", fSctNm='" + fSctNm + '\'' +
                ", fEngNm='" + fEngNm + '\'' +
                ", flowLang='" + flowLang + '\'' +
                ", fContent='" + fContent + '\'' +
                ", fUse='" + fUse + '\'' +
                ", fGrow='" + fGrow + '\'' +
                ", fType='" + fType + '\'' +
                ", imgUrl1='" + imgUrl1 + '\'' +
                ", imgUrl2='" + imgUrl2 + '\'' +
                ", imgUrl3='" + imgUrl3 + '\'' +
                '}';
    }
}
