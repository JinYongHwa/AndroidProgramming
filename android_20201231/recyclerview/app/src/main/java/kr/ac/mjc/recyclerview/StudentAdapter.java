package kr.ac.mjc.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> mStudentList;
    private Context mContext;

    public StudentAdapter(Context context, List<Student> studentList){
        this.mContext=context;
        this.mStudentList=studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("StudentAdapter","onCreateViewHolder");
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_student,parent,false);
        StudentViewHolder studentViewHolder=new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Log.d("StudentAdapter","onBindViewHolder :"+position);
        Student student=mStudentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        Log.d("StudentAdapter","getItemCount :"+mStudentList.size());
        return mStudentList.size();
    }



    class StudentViewHolder extends RecyclerView.ViewHolder{

        ImageView imageIv;
        TextView nameTv;
        TextView numberTv;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv=itemView.findViewById(R.id.image_iv);
            nameTv=itemView.findViewById(R.id.name_tv);
            numberTv=itemView.findViewById(R.id.number_tv);
        }

        public void bind(Student student){
            nameTv.setText(student.getName());
            numberTv.setText(String.valueOf(student.getNumber()));
            imageIv.setImageDrawable(student.getImage());
        }
    }

}

















