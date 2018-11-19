## Student.java
``` java
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Student extends RealmObject {

    private String name;
    @PrimaryKey
    private String studentNumber;
    private int cls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }
}
```

## item_student.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <TextView
        android:id="@+id/name_tv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="5dp"
        android:text="TextView"
        android:textSize="20sp" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <TextView
            android:id="@+id/cls_tv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:padding="5dp"
            android:text="TextView" />

        <TextView
            android:id="@+id/number_tv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:padding="5dp"
            android:text="TextView" />
    </LinearLayout>

</LinearLayout>
```

## StudentItemLayout.java
``` java
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
```

## StudentAdapter.java
``` java
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends BaseAdapter {

    List<Student> mStudentList=new ArrayList<Student>();
    Context mContext;

    public StudentAdapter(Context context, List<Student> sl){
        mContext=context;
        mStudentList=sl;
    }

    @Override
    public int getCount() {
        return mStudentList.size();
    }

    @Override
    public Student getItem(int position) {
        return mStudentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudentItemLayout itemLayout=null;
        if(convertView==null){
            itemLayout=new StudentItemLayout(mContext);
        }
        else{
            itemLayout= (StudentItemLayout) convertView;
        }
        Student student=getItem(position);
        itemLayout.setStudent(student);
        return itemLayout;
    }
}
```

## activity_add_student.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/name_et"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:hint="이름을 입력해주세요"
        android:inputType="textPersonName" />

    <EditText
        android:id="@+id/student_number_et"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:hint="학번을 입력해주세요"
        android:inputType="number" />

    <Spinner
        android:id="@+id/cls_sp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:entries="@array/cls_list" />

    <Button
        android:id="@+id/add_btn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="추가" />

</LinearLayout>
```

## AddStudentActivity.java
``` java
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
```

## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/add_student_btn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="학생추가" />

    <ListView
        android:id="@+id/listview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1" />

</LinearLayout>
```

## MainActivity.java
``` java
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
```
