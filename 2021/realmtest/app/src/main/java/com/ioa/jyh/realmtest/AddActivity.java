package com.ioa.jyh.realmtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText nameEt=findViewById(R.id.name_et);
        EditText numberEt=findViewById(R.id.number_et);
        Spinner clsSp=findViewById(R.id.cls_sp);
        Button addBtn=findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameEt.getText().toString();
                String number=numberEt.getText().toString();
                String cls=clsSp.getSelectedItem().toString();

                cls=cls.replaceAll("반","");
                int intCls=Integer.parseInt(cls);

                Student student=new Student();
                student.setName(name);
                student.setStudentNumber(number);
                student.setCls(intCls);

                Intent intent=new Intent();
                intent.putExtra("student",student);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
