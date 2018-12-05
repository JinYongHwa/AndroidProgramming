package kr.ac.mjc.twich_exmaple;

public class Game {
    private int _id;
    private int giantbomb_id;
    private String name;
    private int popularity;
    private Image box;
    private Image logo;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getGiantbomb_id() {
        return giantbomb_id;
    }

    public void setGiantbomb_id(int giantbomb_id) {
        this.giantbomb_id = giantbomb_id;
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

    public Image getBox() {
        return box;
    }

    public void setBox(Image box) {
        this.box = box;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }
}
