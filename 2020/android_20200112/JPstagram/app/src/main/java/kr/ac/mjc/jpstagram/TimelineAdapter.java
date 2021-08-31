package kr.ac.mjc.jpstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

    List<Post> mPostList;
    Context mContext;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    OnPostClickListener mListener;

    public TimelineAdapter(List<Post> postList,Context context){
        this.mContext=context;
        this.mPostList=postList;
    }

    public void setOnPostClickListener(OnPostClickListener listener){
        mListener=listener;
    }


    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_timeline,parent,false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        Post post=mPostList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    class TimelineViewHolder extends RecyclerView.ViewHolder{

        ImageView profileIv;
        TextView nameTv;
        ImageView imageIv;
        TextView textTv;
        Post mPost;

        public TimelineViewHolder(@NonNull View itemView) {
            super(itemView);
            profileIv=itemView.findViewById(R.id.profile_iv);
            nameTv=itemView.findViewById(R.id.name_tv);
            imageIv=itemView.findViewById(R.id.image_iv);
            textTv=itemView.findViewById(R.id.text_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onClick(mPost);
                    }
                }
            });
        }
        public void bind(Post post){
            mPost=post;
            textTv.setText(post.getText());
            Glide.with(imageIv).load(post.getImageUrl()).into(imageIv);

            firestore.collection("User").document(post.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                         User user=documentSnapshot.toObject(User.class);
                         nameTv.setText(user.getName());
                         if(user.getProfileUrl()!=null){
                             Glide.with(profileIv).load(user.getProfileUrl()).into(profileIv);
                         }
                }
            });
        }
    }

    interface OnPostClickListener{
        public void onClick(Post post);
    }
}
