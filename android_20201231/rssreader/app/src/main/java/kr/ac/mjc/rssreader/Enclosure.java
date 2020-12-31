package kr.ac.mjc.rssreader;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Enclosure implements Serializable {

    @SerializedName("@url")
    private  String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
