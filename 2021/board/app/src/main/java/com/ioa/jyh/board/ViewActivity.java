package com.ioa.jyh.board;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class ViewActivity extends AppCompatActivity {

    TextView titleTv;
    TextView writerTv;
    TextView viewcountTv;
    TextView dateTv;
    TextView bodyTv;

    ProgressBar loadingPb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        titleTv=findViewById(R.id.title_tv);
        writerTv=findViewById(R.id.writer_tv);
        viewcountTv=findViewById(R.id.viewcount_tv);
        dateTv=findViewById(R.id.date_tv);
        bodyTv=findViewById(R.id.body_tv);

        loadingPb=findViewById(R.id.loading_pb);

        int boardId=getIntent().getIntExtra("boardId",-1);
        Board board=new Board();
        board.setId(boardId);
        Common.getBoardService(ViewActivity.this).view(board).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Board boardItem=response.body().getBoard();
                titleTv.setText(boardItem.getTitle());
                writerTv.setText(boardItem.getEmail());
                viewcountTv.setText(Integer.toString(boardItem.getViewCount()));
                dateTv.setText(boardItem.getFormattedCreateDate());
                bodyTv.setText(boardItem.getBody());
                
                loadingPb.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}
