package com.example.moham.takafol.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moham on 6/21/2017.
 */

public class suggestModel {
    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("followed_id")
    @Expose
    private String followId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @SerializedName("image")
    @Expose
    private String userImage;
}
