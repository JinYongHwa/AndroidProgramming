package kr.ac.mjc.sharelocation.model;

public class Room {

    private String title;
    private Location targetLocation;
    private String address;
    private int limitUser=10;

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
