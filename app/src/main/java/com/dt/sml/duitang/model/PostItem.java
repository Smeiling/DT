package com.dt.sml.duitang.model;

import java.io.Serializable;

/**
 * Created by Smeiling on 2017/7/16.
 */

public class PostItem implements Serializable {
    private String headerUrl;
    private String userNick;
    private String contentTitle;
    private String contentText;
    private String contentImgUrl;
    private int reviewCount;
    private int starCount;

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getContentImgUrl() {
        return contentImgUrl;
    }

    public void setContentImgUrl(String contentImgUrl) {
        this.contentImgUrl = contentImgUrl;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }
}
