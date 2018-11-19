package kr.ac.mjc.sqlite_example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

public class AddStudentActivity extends Activity implements View.OnClickListener{

    EditText nameEt;
    EditText studentNumberEt;
    Spinner clsSp;
    Button addStudentBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        nameEt=findViewById(R.id.name_et);
        studentNumberEt=findViewById(R.id.student_number_et);
        clsSp=findViewById(R.id.cls_sp);
        addStudentBtn=findViewById(R.id.add_btn);
        addStudentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_btn){
            String name=nameEt.getText().toString();
            if(name.length()==0){
                showToast("이름을 입력해주세요");
                return;
            }
            String studentNumber=studentNumberEt.getText().toString();
            if(studentNumber.length()==0){
                showToast("학번을 입력해주세요");
                return;
            }

            String clsStr=clsSp.getSelectedItem().toString();
            int clsInt=1;
            switch (clsStr){
                case "1반":
                    clsInt=1;
                    break;
                case "2반":
                    clsInt=2;
                case "3반":
                    clsInt=3;
            }
            Realm realm = Realm.getDefaultInstance();
            Student findStudent=realm.where(Student.class)
                    .equalTo("studentNumber",studentNumber)
                    .findFirst();
            if(findStudent!=null){
                showToast("이미 존재하는 학번입니다");
                return;
            }
            Student student=new Student();
            student.setName(name);
            student.setStudentNumber(studentNumber);
            student.setCls(clsInt);

            realm.beginTransaction();
            realm.copyToRealm(student);
            realm.commitTransaction();

            setResult(RESULT_OK);
            finish();

        }
    }
    public void showToast(String msg){
        Toast.makeText(AddStudentActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}
