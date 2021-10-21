package com.ioa.jyh.board;

import java.util.List;

public class Response {
    private List<Board> boardList;
    private Navigator nav;
    private boolean result;
    private User user;
    private String message;
    private Board board;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Board> getBoardList() {
        return boardList;
    }

    public void setBoardList(List<Board> boardList) {
        this.boardList = boardList;
    }

    public Navigator getNav() {
        return nav;
    }

    public void setNav(Navigator nav) {
        this.nav = nav;
    }
}
