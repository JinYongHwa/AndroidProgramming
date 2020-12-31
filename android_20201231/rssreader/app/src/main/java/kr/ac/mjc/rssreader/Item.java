package kr.ac.mjc.rssreader;

public class Item {
    private String title;
    private String link;
    private String author;
    private Enclosure enclosur;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Enclosure getEnclosur() {
        return enclosur;
    }

    public void setEnclosur(Enclosure enclosur) {
        this.enclosur = enclosur;
    }
}
