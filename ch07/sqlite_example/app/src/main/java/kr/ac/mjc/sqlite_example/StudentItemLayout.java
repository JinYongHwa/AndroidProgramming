package kr.ac.mjc.sqlite_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;

public class StudentItemLayout extends LinearLayout{

    LayoutInflater mInflater;
    TextView nameTv;
    TextView clsTv;
    TextView numberTv;


    public StudentItemLayout(Context context) {
        super(context);
        mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup rootView= (ViewGroup) mInflater.inflate(R.layout.item_student,this,true);
        nameTv=rootView.findViewById(R.id.name_tv);
        clsTv=rootView.findViewById(R.id.cls_tv);
        numberTv=rootView.findViewById(R.id.number_tv);
    }

    public void setStudent(Student student){
        nameTv.setText(student.getName());
        clsTv.setText(String.format("%d반",student.getCls()));
        numberTv.setText(student.getStudentNumber());
    }


}
