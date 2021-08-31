package kr.ac.mjc.recyclerview;

import android.graphics.drawable.Drawable;

public class Student {

    private String name;
    private int number;
    private Drawable image;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
