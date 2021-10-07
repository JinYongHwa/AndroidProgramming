package com.ioa.jyh.board;

import java.util.List;

public class Response {
    private List<Board> boardList;
    private Navigator nav;

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
