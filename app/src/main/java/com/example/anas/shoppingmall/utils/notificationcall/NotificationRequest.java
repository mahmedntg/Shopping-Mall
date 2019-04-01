package com.example.anas.shoppingmall.utils.notificationcall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationRequest implements Serializable {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("data")
    @Expose
    private Notification notification;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }


}