package com.jionchu.speakup.src.course.adapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.ApplicationClass;
import com.jionchu.speakup.src.assignment.AssignmentActivity;
import com.jionchu.speakup.src.course.models.Assignment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentListViewHolder> {

    private ArrayList<Assignment> mAssignmentList;

    public AssignmentAdapter(ArrayList<Assignment> list){
        mAssignmentList = list;
    }

    @NonNull
    @Override
    public AssignmentAdapter.AssignmentListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_assignment,viewGroup,false);
        return new AssignmentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.AssignmentListViewHolder assignmentListViewHolder, int i) {

        assignmentListViewHolder.tvTitle.setText(mAssignmentList.get(i).getAssignmentName());
    }

    class AssignmentListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        AssignmentListViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.item_assignment_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(itemView.getContext(), AssignmentActivity.class);
                    SharedPreferences.Editor editor = ApplicationClass.sSharedPreferences.edit();
                    editor.putInt("assignmentId", mAssignmentList.get(pos).getAssignmentId());
                    editor.apply();
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAssignmentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}