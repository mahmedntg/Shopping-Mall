package com.example.anas.shoppingmall.utils;

import java.io.Serializable;

/**
 * Created by mhamedsayed on 3/25/2019.
 */

public class Mall implements Serializable {
    private int id;
    private double latitude, longitude;
    private String name;

    public Mall() {
    }

    public Mall(int id, double latitude, double longitude, String name) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
