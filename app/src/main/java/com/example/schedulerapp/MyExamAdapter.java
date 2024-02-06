package com.example.schedulerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyExamAdapter extends RecyclerView.Adapter<MyExamAdapter.MyViewHolder> {
    private List<MyExam> examList;

    public MyExamAdapter(List<MyExam> examList) {
        this.examList = examList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyExam myExam = examList.get(position);
        holder.examTitleTextView.setText(myExam.getTitle());
        holder.examDateTextView.setText(myExam.getDate());
        holder.examTimeTextView.setText(myExam.getTime());
        holder.examLocationTextView.setText(myExam.getLocation());
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView examTitleTextView;
        public TextView examDateTextView;
        public TextView examTimeTextView;
        public TextView examLocationTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            examTitleTextView = itemView.findViewById(R.id.textExamTitle);
            examDateTextView = itemView.findViewById(R.id.textExamDate);
            examTimeTextView = itemView.findViewById(R.id.textExamTime);
            examLocationTextView = itemView.findViewById(R.id.textExamLocation);
        }
    }

    public void submitList(List<MyExam> newList) {
        this.examList = newList;
        notifyDataSetChanged();
    }
}
