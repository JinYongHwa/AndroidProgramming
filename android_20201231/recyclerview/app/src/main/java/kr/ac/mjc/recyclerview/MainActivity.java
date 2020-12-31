package kr.ac.mjc.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Student> studentList=new ArrayList<Student>();
    StudentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView listRv=findViewById(R.id.list_rv);
        mAdapter=new StudentAdapter(this,studentList);
        listRv.setAdapter(mAdapter);

        //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        listRv.setLayoutManager(gridLayoutManager);

        for(int i=0;i<1000;i++){
            Student student=new Student();
            student.setName("진용화"+i);
            student.setNumber(2020000000+i);
            if(i%3==0){
                student.setImage(getResources().getDrawable(R.drawable.student1));
            }
            if(i%3==1){
                student.setImage(getResources().getDrawable(R.drawable.student2));
            }
            if(i%3==2){
                student.setImage(getResources().getDrawable(R.drawable.student3));
            }
            studentList.add(student);

        }
        mAdapter.notifyDataSetChanged();


    }
}