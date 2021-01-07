package kr.ac.mjc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        auth=FirebaseAuth.getInstance();

        TextView emailTv=findViewById(R.id.email_tv);
        Button logoutBtn=findViewById(R.id.logout_btn);

        String email=auth.getCurrentUser().getEmail();
        emailTv.setText(email);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent=new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
