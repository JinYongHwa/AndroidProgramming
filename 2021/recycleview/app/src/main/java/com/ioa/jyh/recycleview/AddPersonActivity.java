package com.ioa.jyh.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPersonActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);

        EditText nameEt=findViewById(R.id.name_et);
        EditText phonenumberEt=findViewById(R.id.phonenumber_et);
        EditText addressEt=findViewById(R.id.address_et);
        Button addBtn=findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameEt.getText().toString();
                String phonenumber=phonenumberEt.getText().toString();
                String address=addressEt.getText().toString();

                if(name.equals("")){
                    Toast.makeText(AddPersonActivity.this,"이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phonenumber.equals("")){
                    Toast.makeText(AddPersonActivity.this,"전화번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Person person=new Person();
                person.setName(name);
                person.setPhoneNumber(phonenumber);
                person.setAddress(address);
                Intent intent=new Intent();
                intent.putExtra("person",person);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
