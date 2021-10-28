package com.ioa.jyh.messenger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String writer;
    private String message;
    private Date date;

    public Message(){
        date=new Date();
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
