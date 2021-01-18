package kr.ac.mjc.sharelocation.model;

public class Location {
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
