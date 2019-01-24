package com.vtcac.thuhuong.mytrips.entity;

import java.io.Serializable;

public class TravelBaseEntity implements Serializable {
    String startDt;
    String title;
    String desc;
    String placeName;
    String placeAddr;
    double placeLat;
    String placeId;
    double placeLng;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddr() {
        return placeAddr;
    }

    public void setPlaceAddr(String placeAddr) {
        this.placeAddr = placeAddr;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(double placeLat) {
        this.placeLat = placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public void setPlaceLng(double placeLng) {
        this.placeLng = placeLng;
    }

    @Override
    public String toString() {
        return "TravelBaseEntity{" +
                ", startDt='" + startDt + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", placeName='" + placeName + '\'' +
                ", placeAddr='" + placeAddr + '\'' +
                ", placeLat=" + placeLat +
                ", placeId='" + placeId + '\'' +
                ", placeLng=" + placeLng +
                '}';
    }
}
