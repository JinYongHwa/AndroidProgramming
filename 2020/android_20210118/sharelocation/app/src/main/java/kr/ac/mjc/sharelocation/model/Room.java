package kr.ac.mjc.sharelocation.model;

import java.io.Serializable;

public class Room implements Serializable {
    private String id;
    private String title;
    private String password;
    private Location targetLocation;
    private String address;
    private int limitUser=10;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLimitUser() {
        return limitUser;
    }

    public void setLimitUser(int limitUser) {
        this.limitUser = limitUser;
    }
}
