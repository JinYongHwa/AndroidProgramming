package kr.ac.mjc.jyh.toast_dialog_example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText xOffsetEt=findViewById(R.id.xoffset_et);
        final EditText yOffsetEt=findViewById(R.id.yoffset_et);
        Button toastBtn=findViewById(R.id.toast_btn);


        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xOffset=Integer.parseInt(xOffsetEt.getText().toString());
                int yOffset=Integer.parseInt(yOffsetEt.getText().toString());
                Toast t=Toast.makeText(MainActivity.this,"Hello World",Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER,xOffset,yOffset);
                t.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("안내");
        builder.setMessage("종료하시겠습니까?");
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
