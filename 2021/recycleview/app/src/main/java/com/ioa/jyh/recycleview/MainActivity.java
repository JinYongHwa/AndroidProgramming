package com.ioa.jyh.recycleview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView peopleRv;
    ArrayList<Person> peopleList=new ArrayList<Person>();
    PeopleAdapter peopleAdapter;

    int REQ_ADDPERSON=123456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        peopleRv=findViewById(R.id.people_rv);
        Button addBtn=findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddPersonActivity.class);
                startActivityForResult(intent,REQ_ADDPERSON);
            }
        });
//        for(int i=1;i<=10000;i++){
//            Person person=new Person();
//            person.setName("진용화"+i);
//
//            person.setPhoneNumber(String.format("000-0000-%04d",i));
//            person.setAddress("서울시 "+i);
//            peopleList.add(person);
//        }
        peopleAdapter=new PeopleAdapter(this,peopleList);
        peopleRv.setAdapter(peopleAdapter);

        peopleAdapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Person person) {
                Log.d("onItemClick",person.getName());
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+person.getPhoneNumber()));
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        peopleRv.setLayoutManager(layoutManager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_ADDPERSON && resultCode==RESULT_OK){
            Person person=(Person)data.getSerializableExtra("person");
            peopleList.add(person);
            peopleAdapter.notifyDataSetChanged();
        }
    }
}