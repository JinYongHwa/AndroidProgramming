package kr.ac.mjc.board;


import java.util.ArrayList;
import java.util.List;


public class Navigator {

    private int page;	//현재페이지
    private int start;	//navigator의 시작 페이지
    private int end;	//navigator의 끝 페이지
    private int prevPage;	//이전 navigator 클릭시 page번호
    private int nextPage;	//다음 navigator 클릭시 page번호
    private int lastPage;	//다음 navigator 클릭시 page번호
    private int startNum;	//현재 페이지의 첫번째글 번호
    private boolean prev;	//이전 navigator 존재유무
    private boolean next;	//다음 navigator 존재유무

    int count;	//전체 글갯수

    int itemPerPage = 10;	//한개페이지당 글의갯수
    int navCount = 10;	//하단 navigator의 한번에 보여질 page 갯수

    public Navigator() {

    }
    public Navigator(int itemPerPage,int navCount) {
        this.itemPerPage=itemPerPage;
        this.navCount=navCount;
    }
    public int getSkip(int page) {
        return (page-1)*itemPerPage;
    }


    public List<Integer> getNavArr() {
        ArrayList<Integer> arr=new ArrayList();
        for(int i=start;i<=end;i++) {
            arr.add(i);
        }
        return arr;
    }

    public Navigator getNav(int page,int count) {

        setPage(page);
        setCount(count);

        setStart(((int)Math.floor(page-1)/getNavCount())*getNavCount()+1);
        setEnd(getStart()+getNavCount()-1);


        int totalPage=getTotalPage(count);

        //글이 네비게이션셋일경우
        if(getStart()==1) {
            setPrev(false);
        }
        else {
            int prevPage=(page-1)/getNavCount() *getNavCount() -getNavCount()+1;
            setPrevPage(prevPage);
            setPrev(true);
        }
        //네비게이션 마지막이 글전체페이지수를 초과할경우
        if(getEnd()>=totalPage) {
            setEnd(totalPage);
            setNext(false);
        }
        else {
            setNext(true);
            setNextPage(getStart()+navCount);
        }

        int startNum=count-((page-1)*itemPerPage);
        setStartNum(startNum);


        return this;
    }

    public int getTotalPage(int count) {
        return (int) Math.ceil((float)count / itemPerPage);
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public int getNavCount() {
        return navCount;
    }

    public void setNavCount(int navCount) {
        this.navCount = navCount;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public boolean isPrev() {
        return prev;
    }
    public void setPrev(boolean prev) {
        this.prev = prev;
    }
    public boolean isNext() {
        return next;
    }
    public void setNext(boolean next) {
        this.next = next;
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}