package kr.ac.mjc.board;

import java.util.List;

public class Result {

    private List<Board> list;
    private Navigator nav;
    private Board board;

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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
