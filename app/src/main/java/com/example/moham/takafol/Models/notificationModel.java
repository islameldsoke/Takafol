package com.example.moham.takafol.Models;

/**
 * Created by islam on 24/04/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class notificationModel {
    @SerializedName("notify_date")
    @Expose
    private String notificationDate;
    @SerializedName("by_user_id")
    @Expose
    private String notificationUserId;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("notify_type")
    @Expose
    private String notificationType;

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public void setNotificationUserId(String notificationUserId) {
        this.notificationUserId = notificationUserId;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getNotificationUserId() {
        return notificationUserId;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getPostId() {
        return postId;
    }

    public String getNotificationType() {
        return notificationType;
    }
}
