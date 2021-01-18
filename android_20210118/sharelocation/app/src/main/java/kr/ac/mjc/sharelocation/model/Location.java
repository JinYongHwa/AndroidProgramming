package kr.ac.mjc.sharelocation.model;

import java.io.Serializable;

public class Location implements Serializable {
    private double lat;
    private double lng;

    public Location(){}

    public Location(double latitude,double longtitude){
        this.lat=latitude;
        this.lng=longtitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
