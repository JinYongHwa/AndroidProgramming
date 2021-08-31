package kr.ac.mjc.storage;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView imageIv=findViewById(R.id.image_iv);

        Image image= (Image) getIntent().getSerializableExtra("image");
        Glide.with(imageIv).load(image.getImageUrl()).into(imageIv);
    }
}
