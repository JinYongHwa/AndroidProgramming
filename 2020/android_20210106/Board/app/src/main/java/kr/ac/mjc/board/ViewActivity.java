package kr.ac.mjc.board;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewActivity extends AppCompatActivity{

    TextView titleTv;
    TextView writeDateTv;
    TextView writerTv;
    TextView viewCountTv;
    TextView textTv;

    FloatingActionButton modifyBtn;
    FloatingActionButton deleteBtn;

    Handler handler=new Handler();
    BoardService boardService;

    int REQ_MODIFY=1003;

    int mId;

    boolean isModifyBoard=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        titleTv=findViewById(R.id.title_tv);
        writeDateTv=findViewById(R.id.write_date_tv);
        writerTv=findViewById(R.id.writer_tv);
        viewCountTv=findViewById(R.id.view_count_tv);
        textTv=findViewById(R.id.text_tv);

        modifyBtn=findViewById(R.id.modify_btn);
        deleteBtn=findViewById(R.id.delete_btn);

        final int id=getIntent().getIntExtra("id",-1);
        mId=id;
        Board board=new Board();
        board.setId(id);

        boardService=BoardUtil.getInstance(this).getBoardService();

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewActivity.this,ModifyActivity.class);
                intent.putExtra("id",id);
                startActivityForResult(intent,REQ_MODIFY);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ViewActivity.this)
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteBoard(id);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });


        boardService.userinfo().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                final boolean result=response.body().isResult();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                      if(result){
                          modifyBtn.setVisibility(View.VISIBLE);
                          deleteBtn.setVisibility(View.VISIBLE);
                      }
                      else{
                          modifyBtn.setVisibility(View.GONE);
                          deleteBtn.setVisibility(View.GONE);
                      }
                    }
                });
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

        getBoard(id);
    }

    public void getBoard(int id){
        Call<Result> result=boardService.item(id);
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("viewActivity",response.toString());
                Log.d("viewActivity",response.message());
                try {
                    Log.d("viewActivity",response.errorBody().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final Board board=response.body().getBoard();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        titleTv.setText(board.getTitle());
                        writeDateTv.setText(board.getFormattedWriteDate());
                        writerTv.setText(board.getWriter());
                        viewCountTv.setText(String.format("조회수 %d",board.getViewCount()));
                        textTv.setText(board.getText());

                    }
                });
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("viewActivity",t.toString());
            }
        });
    }

    public void deleteBoard(int id){
        boardService.remove(id).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                boolean result=response.body().isResult();
                if(result){
                    Toast.makeText(ViewActivity.this,"게시물이 삭제되었습니다",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
                else{
                    Toast.makeText(ViewActivity.this,"삭제가 실패하였습니다",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_MODIFY&&resultCode==RESULT_OK){
            isModifyBoard=true;
            getBoard(mId);
        }
    }

    @Override
    public void onBackPressed() {
        if(isModifyBoard){
            setResult(RESULT_OK);
        }

        finish();
    }
}
