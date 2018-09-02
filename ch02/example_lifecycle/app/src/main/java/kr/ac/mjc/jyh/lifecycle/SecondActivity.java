package kr.ac.mjc.jyh.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("SecondActivity","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SecondActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SecondActivity","onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SecondActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SecondActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondActivity","onDestroy");
    }
}
