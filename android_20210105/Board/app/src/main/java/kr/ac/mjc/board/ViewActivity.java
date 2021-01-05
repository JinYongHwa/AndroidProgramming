package kr.ac.mjc.board;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ViewActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        TextView titleTv=findViewById(R.id.title_tv);
        TextView writeDateTv=findViewById(R.id.write_date_tv);
        TextView writerTv=findViewById(R.id.writer_tv);
        TextView viewCountTv=findViewById(R.id.view_count_tv);
        TextView textTv=findViewById(R.id.text_tv);

        int id=getIntent().getIntExtra("id",-1);
    }
}
