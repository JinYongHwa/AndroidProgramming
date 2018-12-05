package kr.ac.mjc.twich_example;

public class Game {
    private int _id;
    private Image box;
    private int giantbomb_id;
    private Image logo;
    private String name;
    private int popularity;

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

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
