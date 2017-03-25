package com.example.moham.takafol.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moham on 3/22/2017.
 */

public class Post {
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("supporters_number")
    @Expose
    private String supportersNumber;
    @SerializedName("shares_number")
    @Expose
    private String sharesNumber;
    @SerializedName("comments_number")
    @Expose
    private String commentsNumber;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image")
    @Expose
    private String image;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getSupportersNumber() {
        return supportersNumber;
    }

    public void setSupportersNumber(String supportersNumber) {
        this.supportersNumber = supportersNumber;
    }

    public String getSharesNumber() {
        return sharesNumber;
    }

    public void setSharesNumber(String sharesNumber) {
        this.sharesNumber = sharesNumber;
    }

    public String getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(String commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
