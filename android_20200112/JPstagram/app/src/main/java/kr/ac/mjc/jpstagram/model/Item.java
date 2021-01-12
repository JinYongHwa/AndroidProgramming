package kr.ac.mjc.jpstagram.model;

public class Item {

    private String title;
    private String link;
    private String image;
    private long lprice;
    private long hprice;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getLprice() {
        return lprice;
    }

    public void setLprice(long lprice) {
        this.lprice = lprice;
    }

    public long getHprice() {
        return hprice;
    }

    public void setHprice(long hprice) {
        this.hprice = hprice;
    }
}
