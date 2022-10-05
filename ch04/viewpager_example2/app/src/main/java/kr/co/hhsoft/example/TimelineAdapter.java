package kr.co.hhsoft.example;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    ArrayList<Post> mPostList;

    public TimelineAdapter(ArrayList<Post> postList){
        this.mPostList=postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post=mPostList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView imageIv;
        TextView textTv;

        String[] messages={"고양이사진","이것은 고양이","안녕 고양이"};
        ViewPagerAdapter adapter;
        ViewPager2 viewPager;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager= itemView.findViewById(R.id.viewpager);
//            imageIv=itemView.findViewById(R.id.image_iv);
            textTv=itemView.findViewById(R.id.text_tv);


        }

        public void bind(Post post){
            adapter=new ViewPagerAdapter();
            viewPager.setAdapter(adapter);
//            Glide.with(imageIv).load(post.getUrl()).centerCrop().into(imageIv);
            Random random=new Random();
            int i= 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                i = random.ints(0,messages.length).findFirst().getAsInt();
            }
            textTv.setText(messages[i]);
            Log.d("jyh",String.format("random[%d]",i));

        }

    }
}
