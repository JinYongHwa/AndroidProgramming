package kr.co.hhsoft.test;

public class KakaoLocation {
    private String cateogry_name;
    private double x;
    private double y;
    private String place_name;
    private String place_url;

    public String getCateogry_name() {
        return cateogry_name;
    }

    public void setCateogry_name(String cateogry_name) {
        this.cateogry_name = cateogry_name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getPlace_url() {
        return place_url;
    }

    public void setPlace_url(String place_url) {
        this.place_url = place_url;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }
}
