package kr.ac.mjc.sqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    final int REQ_ADD=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        Button addBtn=findViewById(R.id.add_btn);
        RecyclerView listRv=findViewById(R.id.list_rv);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,REQ_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_ADD&&resultCode==RESULT_OK){
            Realm realm= Realm.getDefaultInstance();

            List<Student> studentList=realm.where(Student.class).findAll();
            for(Student student:studentList){
                Log.d("student",String.format("[%s][%s][%d]",student.getName(),student.getStudentNumber(),student.getCls()));
            }
        }

    }
}











