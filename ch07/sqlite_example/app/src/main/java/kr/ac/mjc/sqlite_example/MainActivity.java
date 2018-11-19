package kr.ac.mjc.sqlite_example;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    Button addStudentBtn;
    StudentAdapter mStudentAdapter;


    final int REQUEST_ADD_STUDENT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addStudentBtn=findViewById(R.id.add_student_btn);
        listView=findViewById(R.id.listview);
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        List<Student> studentList=realm.where(Student.class)
                .findAll();

        mStudentAdapter=new StudentAdapter(this,studentList);
        listView.setAdapter(mStudentAdapter);
        listView.setOnItemClickListener(this);

        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddStudentActivity.class);
                startActivityForResult(intent,REQUEST_ADD_STUDENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_ADD_STUDENT && resultCode==RESULT_OK){
            mStudentAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Student student=mStudentAdapter.getItem(position);
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Student.class)
                .equalTo("studentNumber",student.getStudentNumber())
                .findFirst().deleteFromRealm();
        realm.commitTransaction();
        mStudentAdapter.notifyDataSetChanged();
    }
}
