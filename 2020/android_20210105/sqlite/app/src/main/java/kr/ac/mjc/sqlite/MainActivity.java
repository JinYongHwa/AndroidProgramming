package kr.ac.mjc.sqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements StudentAdapter.OnStudentClickListener {

    final int REQ_ADD=1000;

    StudentAdapter mStudentAdapter;
    List<Student> mStudentList=new ArrayList<>();

    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        mRealm=Realm.getDefaultInstance();

        Button addBtn=findViewById(R.id.add_btn);
        RecyclerView listRv=findViewById(R.id.list_rv);

        mStudentAdapter=new StudentAdapter(this,mStudentList);
        mStudentAdapter.setOnStudentClickListener(this);
        listRv.setAdapter(mStudentAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        listRv.setLayoutManager(linearLayoutManager);

        selectStudentList();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,REQ_ADD);
            }
        });
    }

    public void selectStudentList(){
        List<Student> tmpStudentList= mRealm.where(Student.class)
                .findAll();
        mStudentList.clear();
        mStudentList.addAll(tmpStudentList);
        mStudentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_ADD&&resultCode==RESULT_OK){
            selectStudentList();
        }

    }

    @Override
    public void onLongClick(Student student) {
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("삭제")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRealm.beginTransaction();
                        mRealm.where(Student.class)
                                .equalTo("studentNumber",student.getStudentNumber())
                                .findFirst().deleteFromRealm();
                        mRealm.commitTransaction();
                        selectStudentList();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();


    }
}











