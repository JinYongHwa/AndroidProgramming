package kr.ac.mjc.http_example;

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

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity {

    Handler handler=new Handler();
    TextView outputTv;

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
                String urlStr=urlEt.getText().toString();
                ConnectThread connectThread=new ConnectThread(urlStr);
                connectThread.start();
            }
        });
    }


    class ConnectThread extends Thread {

        private String mUrl;
        public ConnectThread(String url){
            this.mUrl=url;
        }
        @Override
        public void run() {
            super.run();
            final String output=request(mUrl);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    outputTv.setText(output);
                }
            });
        }

        public String request(String urlStr){
            StringBuilder output=new StringBuilder();
            try {
                URL url=new URL(urlStr);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                if(conn!=null){

                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int responseCode=conn.getResponseCode();
                    if(responseCode==HttpURLConnection.HTTP_OK){
                        BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while(true){
                            String line=reader.readLine();
                            if(line==null){
                                break;
                            }
                            output.append(line+"\n");
                        }

                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }



}
