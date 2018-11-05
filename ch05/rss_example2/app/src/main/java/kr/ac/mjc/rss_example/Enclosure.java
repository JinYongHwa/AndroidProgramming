package kr.ac.mjc.rss_example;

import com.google.gson.annotations.SerializedName;

public class Enclosure {

    @SerializedName("@url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
