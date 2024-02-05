package com.example.schedulerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.MyAssignment;

import java.util.List;

public class MyAssignmentAdapter extends RecyclerView.Adapter<MyAssignmentAdapter.MyViewHolder> {

    private List<MyAssignment> assignmentList;

    public MyAssignmentAdapter(List<MyAssignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyAssignment assignment = assignmentList.get(position);
        holder.titleTextView.setText(assignment.getTitle());
        holder.dueDateTextView.setText(assignment.getDueDate());
        holder.associatedClassTextView.setText(assignment.getAssociatedClass());
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public void setAssignments(List<MyAssignment> assignments) {
        this.assignmentList = assignments;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView dueDateTextView;
        public TextView associatedClassTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textAssignmentName);
            dueDateTextView = itemView.findViewById(R.id.textAssignmentDate);
            associatedClassTextView = itemView.findViewById(R.id.textAssignmentClass);
        }
    }
}
