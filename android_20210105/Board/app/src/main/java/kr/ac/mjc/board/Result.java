package kr.ac.mjc.board;

import java.util.List;

public class Result {

    private List<Board> list;
    private Navigator nav;

    public List<Board> getList() {
        return list;
    }

    public void setList(List<Board> list) {
        this.list = list;
    }

    public Navigator getNav() {
        return nav;
    }

    public void setNav(Navigator nav) {
        this.nav = nav;
    }
}
