package kr.ac.mjc.sqlite;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
