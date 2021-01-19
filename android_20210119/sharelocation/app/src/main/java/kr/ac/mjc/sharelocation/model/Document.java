package kr.ac.mjc.sharelocation.model;

public class Document {

    private Address road_address;
    private Address address;

    public Address getRoad_address() {
        return road_address;
    }

    public void setRoad_address(Address road_address) {
        this.road_address = road_address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
