package kr.ac.mjc.board;

import java.util.List;

public class Result {

    private List<Board> list;
    private Navigator nav;
    private Board board;
    private boolean isUse;
    private boolean result;
    private String message;


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
