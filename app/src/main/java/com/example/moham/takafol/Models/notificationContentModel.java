package com.example.moham.takafol.Models;

/**
 * Created by islam on 27/04/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class notificationContentModel {

    @SerializedName("content")
    @Expose
    private String postContent;
    @SerializedName("post_date")
    @Expose
    private String post_date;
    @SerializedName("supporters_number")
    @Expose
    private String supporters_number;
    @SerializedName("shares_number")
    @Expose
    private String shares_number;
    @SerializedName("name of last liker")
    @Expose
    private String lastLiker;
    @SerializedName("comments_number")
    @Expose
    private String comments_number;
    @SerializedName("name of last commente")
    @Expose
    private String lastCommenter;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("user_image")
    @Expose
    private String user_image;


    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public void setSupporters_number(String supporters_number) {
        this.supporters_number = supporters_number;
    }

    public void setShares_number(String shares_number) {
        this.shares_number = shares_number;
    }

    public void setLastLiker(String lastLiker) {
        this.lastLiker = lastLiker;
    }

    public void setComments_number(String comments_number) {
        this.comments_number = comments_number;
    }

    public void setLastCommenter(String lastCommenter) {
        this.lastCommenter = lastCommenter;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPost_date() {
        return post_date;
    }

    public String getSupporters_number() {
        return supporters_number;
    }

    public String getShares_number() {
        return shares_number;
    }

    public String getLastLiker() {
        return lastLiker;
    }

    public String getComments_number() {
        return comments_number;
    }

    public String getLastCommenter() {
        return lastCommenter;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_image() {
        return user_image;
    }
}
