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
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("image")
    @Expose
    private String image;
    private String like_status;
    private String needed_money;
    private String donated_money;
    private String user_email;
    private String user_phone;

    public String getTrust_number() {
        return trust_number;
    }

    public void setTrust_number(String trust_number) {
        this.trust_number = trust_number;
    }

    public String getUntrust_number() {
        return untrust_number;
    }

    public void setUntrust_number(String untrust_number) {
        this.untrust_number = untrust_number;
    }

    public String getTrust_status() {
        return trust_status;
    }

    public void setTrust_status(String trust_status) {
        this.trust_status = trust_status;
    }

    private String trust_number;
    private String untrust_number;
    private String trust_status;

    public String getNeeded_money() {
        return needed_money;
    }

    public void setNeeded_money(String needed_money) {
        this.needed_money = needed_money;
    }

    public String getDonated_money() {
        return donated_money;
    }

    public void setDonated_money(String donated_money) {
        this.donated_money = donated_money;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getLike_status() {
        return like_status;
    }

    public void setLike_status(String like_status) {
        this.like_status = like_status;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
