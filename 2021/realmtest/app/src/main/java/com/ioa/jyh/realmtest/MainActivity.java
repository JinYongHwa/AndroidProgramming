package com.ioa.jyh.realmtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements StudentAdapter.OnItemLongClickListener {

    final int REQ_ADD=10000;
    static final String realmName = "student";
    List<Student> studentList=new ArrayList<>();

    StudentAdapter studentAdapter=new StudentAdapter(this,studentList);

    RealmConfiguration config = new RealmConfiguration.Builder()
            .name(realmName)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView listRv=findViewById(R.id.list_rv);
        listRv.setAdapter(studentAdapter);
        studentAdapter.setOnItemLongClickListener(this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        listRv.setLayoutManager(linearLayoutManager);

        Button addBtn=findViewById(R.id.add_btn);


        Realm realm=Realm.getInstance(config);
        RealmResults<Student> realmStudentList=realm.where(Student.class).findAll();
        studentList.addAll(realmStudentList);

        studentAdapter.notifyDataSetChanged();

        for(Student student:studentList){
            Log.d("MainActivity",student.getName());
        }


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,REQ_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_ADD&&resultCode==RESULT_OK){
            Student student=(Student) data.getSerializableExtra("student");


            Realm backgroundThreadRealm = Realm.getInstance(config);
            backgroundThreadRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(student);
                    RealmResults<Student> realmResults=realm.where(Student.class).findAll();
                    studentList.clear();
                    studentList.addAll(realmResults);
                    studentAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onItemLongClick(Student student) {
        new AlertDialog.Builder(this)
                .setMessage( String.format("%s 학생을 삭제하시겠습니까?",student.getName()) )
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Realm realm=Realm.getInstance(config);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                student.deleteFromRealm();
                                studentList.clear();
                                studentList.addAll(realm.where(Student.class).findAll());
                                studentAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}








