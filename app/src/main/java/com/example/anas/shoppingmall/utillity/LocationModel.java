package com.example.anas.shoppingmall.utillity;

import java.util.Date;

public class LocationModel {
    private String id;
    private String placeName;
    private String title;
    private double latitude;
    private double longitude;
    private String userId;
    private Date date;

    public LocationModel() {
    }

    public LocationModel(String placeName, double latitude, double longitude, String title) {
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
