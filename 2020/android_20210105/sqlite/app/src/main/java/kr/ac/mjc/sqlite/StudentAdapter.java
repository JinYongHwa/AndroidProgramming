package kr.ac.mjc.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Context mContext;
    List<Student> mStudentList;

    OnStudentClickListener mLisetner;

    public StudentAdapter(Context context, List<Student> studentList){
        this.mContext=context;
        this.mStudentList=studentList;
    }

    public void setOnStudentClickListener(OnStudentClickListener listener){
        this.mLisetner=listener;
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_student,parent,false);
        StudentViewHolder studentViewHolder=new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student=mStudentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv;
        TextView numberTv;
        TextView clsTv;
        Student mStudent;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.name_tv);
            numberTv=itemView.findViewById(R.id.number_tv);
            clsTv=itemView.findViewById(R.id.cls_tv);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(mLisetner!=null){
                        mLisetner.onLongClick(mStudent);
                    }
                    return false;
                }
            });
        }
        public void bind(Student student){
            nameTv.setText(student.getName());
            numberTv.setText(student.getStudentNumber());
            clsTv.setText(String.valueOf(student.getCls()));
            mStudent=student;
        }
    }

    interface OnStudentClickListener{
        public void onLongClick(Student student);
    }
}
