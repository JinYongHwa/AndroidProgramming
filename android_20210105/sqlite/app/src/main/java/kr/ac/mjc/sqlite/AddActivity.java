package kr.ac.mjc.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText nameEt=findViewById(R.id.name_et);
        EditText numberEt=findViewById(R.id.number_et);
        EditText classET=findViewById(R.id.class_et);

        Button addBtn=findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEt.getText().toString();
                String number=numberEt.getText().toString();
                String cls=classET.getText().toString();
                if(name.length()==0){
                    Toast.makeText(AddActivity.this,"이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(number.length()==0){
                    Toast.makeText(AddActivity.this,"학번을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cls.length()==0){
                    Toast.makeText(AddActivity.this,"반을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                Student student=new Student();
                student.setName(name);
                student.setStudentNumber(number);
                student.setCls(Integer.parseInt(cls));

                Realm realm=Realm.getDefaultInstance();

                Student findStudent=realm.where(Student.class)
                        .equalTo("studentNumber",student.getStudentNumber())
                        .findFirst();
                if(findStudent!=null){
                    Toast.makeText(AddActivity.this,"이미 입력된 학번입니다",Toast.LENGTH_SHORT).show();
                    return;
                }


                realm.beginTransaction();
                realm.copyToRealm(student);
                realm.commitTransaction();

                setResult(RESULT_OK);
                finish();

            }
        });

    }
}
