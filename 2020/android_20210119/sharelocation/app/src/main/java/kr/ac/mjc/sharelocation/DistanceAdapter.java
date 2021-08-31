package kr.ac.mjc.sharelocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.ac.mjc.sharelocation.model.User;

public class DistanceAdapter extends RecyclerView.Adapter<DistanceAdapter.DistanceViewHolder> {

    Context mContext;
    List<User> mUserList;

    public DistanceAdapter(Context context,List<User> userList){
        this.mContext=context;
        this.mUserList=userList;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_distance,parent,false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DistanceViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv;
        TextView timeTv;
        TextView distanceTv;


        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.name_tv);
            timeTv=itemView.findViewById(R.id.time_tv);
            distanceTv=itemView.findViewById(R.id.distance_tv);
        }
        public void bind(User user){
            nameTv.setText(user.getName());
            timeTv.setText(String.format("%d분",user.getTime()));
            distanceTv.setText(String.format("%.2fkm",user.getDistance()));
        }
    }
}
