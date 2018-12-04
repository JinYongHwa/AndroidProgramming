package kr.co.hhsoft.twitch_example;

public class Game {
    int _id;
    Image box;
    int giantbomb_id;
    Image logo;
    String name;
    long popularity;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Image getBox() {
        return box;
    }

    public void setBox(Image box) {
        this.box = box;
    }

    public int getGiantbomb_id() {
        return giantbomb_id;
    }

    public void setGiantbomb_id(int giantbomb_id) {
        this.giantbomb_id = giantbomb_id;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }
}
