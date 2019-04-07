package com.example.anas.shoppingmall.utillity;

import java.io.Serializable;

/**
 * Created by mhamedsayed on 3/15/2019.
 */

public class Store implements Serializable {
    private String name, category, openTime, closeTime, saleFromDate, saleToDate, image, description, key;

    public Store() {
    }

    public Store(String name, String category, String openTime, String closeTime, String image, String description) {
        this.name = name;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getSaleFromDate() {
        return saleFromDate;
    }

    public void setSaleFromDate(String saleFromDate) {
        this.saleFromDate = saleFromDate;
    }

    public String getSaleToDate() {
        return saleToDate;
    }

    public void setSaleToDate(String saleToDate) {
        this.saleToDate = saleToDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
