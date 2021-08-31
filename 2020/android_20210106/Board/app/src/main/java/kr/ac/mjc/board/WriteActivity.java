package kr.ac.mjc.board;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        final EditText titleEt=findViewById(R.id.title_et);
        final EditText textEt=findViewById(R.id.text_et);

        Button submitBtn=findViewById(R.id.submit_btn);
        Handler handler=new Handler();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleEt.getText().toString();
                String text=textEt.getText().toString();
                if(title.length()==0){
                    Toast.makeText(WriteActivity.this,"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(text.length()==0){
                    Toast.makeText(WriteActivity.this,"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                BoardService boardService=BoardUtil.getInstance(WriteActivity.this).getBoardService();
                Call<Result> call=boardService.write(title,text);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call,final Response<Result> response) {
                        boolean result=response.body().isResult();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(result){
                                    setResult(RESULT_OK);
                                    finish();
                                }
                                else {
                                    new AlertDialog.Builder(WriteActivity.this)
                                            .setMessage(response.body().getMessage())
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent=new Intent(WriteActivity.this,LoginActivity.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .show();
                                }
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }
        });
    }
}
