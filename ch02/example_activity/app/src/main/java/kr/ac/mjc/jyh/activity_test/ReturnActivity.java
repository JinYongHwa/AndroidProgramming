package kr.ac.mjc.jyh.activity_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReturnActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        Button return_btn=findViewById(R.id.return_btn);
        final EditText value_et=findViewById(R.id.value_et);

        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=value_et.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("value",value);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }
}
