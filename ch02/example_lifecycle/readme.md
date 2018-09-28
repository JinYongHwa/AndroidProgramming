## MainActivity.java
```
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity","onCreate");
        setContentView(R.layout.activity_main);

        Button startBtn=findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy");
    }
}
```

## SecondActivity.java
```
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

```

## AndroidManifest.xml
```
<activity android:name=".SecondActivity"></activity>
```
