package kr.ac.mjc.okhttp_example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
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
                String output= null;
                ConnectThread connectThread=new ConnectThread(urlEt.getText().toString());
                connectThread.start();
            }
        });
    }



    class ConnectThread extends Thread {
        OkHttpClient client = new OkHttpClient();
        private String mUrl;

        public ConnectThread(String url) {
            this.mUrl = url;
        }

        @Override
        public void run() {
            super.run();

            try {
                final String output = request(mUrl);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        outputTv.setText(output);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String request(String urlStr) throws IOException {
            Request request = new Request.Builder()
                    .url(urlStr)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }
}
