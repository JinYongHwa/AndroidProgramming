package kr.ac.mjc.jyh.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class CountService extends Service implements Runnable{


    Handler handler=new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread myThread=new Thread(this);
        myThread.start();

    }

    @Override
    public void run() {
        int count=0;

        while(true){
            count++;
            final int finalCount=count;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CountService.this,"현재카운트 : "+finalCount,Toast.LENGTH_SHORT).show();
                }
            });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count>=5){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(CountService.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            }
        }
    }
}
