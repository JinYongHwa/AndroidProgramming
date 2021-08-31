package kr.ac.mjc.board;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyActivity extends AppCompatActivity {

    BoardService boardService;
    Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        EditText titleEt=findViewById(R.id.title_et);
        EditText textEt=findViewById(R.id.text_et);
        Button submitBtn=findViewById(R.id.submit_btn);
        submitBtn.setText("수정");

        boardService=BoardUtil.getInstance(this).getBoardService();

        final int id=getIntent().getIntExtra("id",-1);
        Log.d("ModifyActivity",String.valueOf(id));
        boardService.item(id).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Board board=response.body().getBoard();
                titleEt.setText(board.getTitle());
                textEt.setText(board.getText());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=titleEt.getText().toString();
                String text=textEt.getText().toString();
                Board board =new Board();
                board.setId(id);
                board.setTitle(title);
                board.setText(text);
                boardService.modify(board).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        boolean result=response.body().isResult();
                        if(result){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ModifyActivity.this,"게시물이 수정되었습니다",Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }
        });
    }
}
