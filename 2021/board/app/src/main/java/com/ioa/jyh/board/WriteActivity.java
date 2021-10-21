package com.ioa.jyh.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class WriteActivity extends AppCompatActivity {

    EditText titleEt;
    EditText bodyEt;
    TextView writerTv;
    Button writeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        titleEt=findViewById(R.id.title_et);
        bodyEt=findViewById(R.id.body_et);
        writerTv=findViewById(R.id.writer_tv);
        writeBtn=findViewById(R.id.write_btn);

        Common.getBoardService(WriteActivity.this).userinfo().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.body().isResult()){
                    User user=response.body().getUser();
                    writerTv.setText(user.getEmail());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=titleEt.getText().toString();
                String body=bodyEt.getText().toString();

                if(title.equals("")){
                    Toast.makeText(WriteActivity.this,"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(body.equals("")){
                    Toast.makeText(WriteActivity.this,"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                write(title,body);
            }
        });
    }

    public void write(String title,String body){
        Board board=new Board();
        board.setTitle(title);
        board.setBody(body);
        Common.getBoardService(WriteActivity.this).write(board).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.body().isResult()){
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}
