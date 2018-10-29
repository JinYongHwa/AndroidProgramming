package kr.ac.mjc.okhttp_example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity implements Callback{
    OkHttpClient client = new OkHttpClient();
    TextView outputTv;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button requestBtn=findViewById(R.id.request_btn);
        final EditText urlEt=findViewById(R.id.url_et);
        outputTv=findViewById(R.id.output_tv);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(urlEt.getText().toString())
                        .build();
                client.newCall(request).enqueue(MainActivity.this);

            }
        });
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.code()==200){
            final String output=response.body().string();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    outputTv.setText(output);
                }
            });
        }
    }
}
