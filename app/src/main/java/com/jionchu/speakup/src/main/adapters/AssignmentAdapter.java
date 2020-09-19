package com.jionchu.speakup.src.main.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jionchu.speakup.R;
import com.jionchu.speakup.src.assignment.AssignmentActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentListViewHolder> {

    private ArrayList<String> mAssignmentList;

    public AssignmentAdapter(ArrayList<String> list){
        mAssignmentList = list;
    }

    @NonNull
    @Override
    public AssignmentAdapter.AssignmentListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_assignment,viewGroup,false);
        return new AssignmentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.AssignmentListViewHolder AssignmentListViewHolder, int i) {

        AssignmentListViewHolder.tvTitle.setText(mAssignmentList.get(i));
    }

    static class AssignmentListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        AssignmentListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.item_assignment_title);

            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(tvTitle.getContext(), AssignmentActivity.class);
                    tvTitle.getContext().startActivity(intent);
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