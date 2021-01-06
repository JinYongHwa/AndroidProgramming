package kr.ac.mjc.board;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    BoardService boardService;

    private String checkEmail="";
    private boolean isUse=false;

    Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        boardService=BoardUtil.getInstance().getBoardService();

        EditText nameEt=findViewById(R.id.name_et);
        final EditText emailEt=findViewById(R.id.email_et);

        Button emailCheckBtn=findViewById(R.id.email_check_btn);

        final EditText passwordEt=findViewById(R.id.password_et);
        EditText passwordConfirmEt=findViewById(R.id.password_confirm_et);

        Button joinBtn=findViewById(R.id.join_btn);


        emailCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEt.getText().toString();
                Call<Result> call=boardService.checkEmail(email);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d("JoinActivity",response.toString());
                        Log.d("JoinActivity",String.valueOf(response.body().isUse()));

                        isUse=response.body().isUse();
                        checkEmail=email;


                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(JoinActivity.this)
                                        .setMessage(response.body().getMessage())
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.d("JoinActivity",t.toString());
                    }
                });
            }
        });

    }
}
