package kr.ac.mjc.board;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        titleTv=findViewById(R.id.title_tv);
        writeDateTv=findViewById(R.id.write_date_tv);
        writerTv=findViewById(R.id.writer_tv);
        viewCountTv=findViewById(R.id.view_count_tv);
        textTv=findViewById(R.id.text_tv);

        int id=getIntent().getIntExtra("id",-1);
        Board board=new Board();
        board.setId(id);

        BoardService boardService=BoardUtil.getInstance().getBoardService();
        Call<Result> result=boardService.item(id);
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("viewActivity",response.toString());
                Log.d("viewActivity",response.message());

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
}
