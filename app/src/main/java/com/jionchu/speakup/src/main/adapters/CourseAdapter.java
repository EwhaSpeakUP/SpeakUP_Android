package com.jionchu.speakup.src.main.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.course.CourseActivity;
import com.jionchu.speakup.src.main.models.Course;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseListViewHolder> {

    private ArrayList<Course> mCourseList;

    public CourseAdapter(ArrayList<Course> list){
        mCourseList = list;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course,viewGroup,false);
        return new CourseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseListViewHolder courseListViewHolder, int i) {

        courseListViewHolder.tvTitle.setText(mCourseList.get(i).getSubjectName());
    }

    class CourseListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        CourseListViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.item_course_title);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();

                Intent intent = new Intent(itemView.getContext(), CourseActivity.class);
                intent.putExtra("courseId", mCourseList.get(pos).getCourseId());
                intent.putExtra("courseName", mCourseList.get(pos).getSubjectName());
                itemView.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}