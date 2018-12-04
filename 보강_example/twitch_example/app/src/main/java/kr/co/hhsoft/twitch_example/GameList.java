package kr.co.hhsoft.twitch_example;

import java.util.ArrayList;

public class GameList {
    int _total;
    ArrayList<Top> top;

    public int get_total() {
        return _total;
    }

    public void set_total(int _total) {
        this._total = _total;
    }

    public ArrayList<Top> getTop() {
        return top;
    }

    public void setTop(ArrayList<Top> top) {
        this.top = top;
    }
}
