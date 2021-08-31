package kr.ac.mjc.twich_example;

import java.util.ArrayList;

public class GameTopList {
    private int _total;
    private ArrayList<Top> top=new ArrayList<Top>();

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
