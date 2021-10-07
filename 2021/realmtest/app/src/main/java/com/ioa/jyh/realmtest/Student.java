package com.ioa.jyh.realmtest;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

public class Student extends RealmObject implements Serializable {

    private String studentNumber;
    private String name;
    private int cls;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }
}
