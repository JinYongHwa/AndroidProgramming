package kr.ac.mjc.jpstagram;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

    private String imageUrl;
    private String text;
    private String userId;
    private Date date;

    public Post(){

        date=new Date();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
