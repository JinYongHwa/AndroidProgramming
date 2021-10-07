package com.ioa.jyh.realmtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    Context mContext;
    List<Student> mStudentList;
    OnItemLongClickListener listener;

    public StudentAdapter(Context context,List<Student> studentList){
        this.mContext=context;
        this.mStudentList=studentList;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_student,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student=mStudentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView numberTv;
        TextView clsTv;
        Student mStudent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.name_tv);
            numberTv=itemView.findViewById(R.id.number_tv);
            clsTv=itemView.findViewById(R.id.cls_tv);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(mStudent);
                    return false;
                }
            });
        }
        public void bind(Student studnet){
            this.mStudent=studnet;
            nameTv.setText(studnet.getName());
            numberTv.setText(studnet.getStudentNumber());
            clsTv.setText( String.format("%d",studnet.getCls()) );
        }
    }

    interface OnItemLongClickListener{
        public void onItemLongClick(Student student);
    }
}
