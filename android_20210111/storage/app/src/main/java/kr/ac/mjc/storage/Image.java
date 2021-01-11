package kr.ac.mjc.storage;

import java.util.Date;

public class Image {

    private String imageUrl;
    private Date date;

    public Image(){
        date=new Date();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
