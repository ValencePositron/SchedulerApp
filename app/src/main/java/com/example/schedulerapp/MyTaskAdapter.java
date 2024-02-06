package com.example.schedulerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.schedulerapp.R;
import java.util.List;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyViewHolder> {
    private List<MyTask> taskList;

    public MyTaskAdapter(List<MyTask> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyTask myTask = taskList.get(position);
        holder.taskTitleTextView.setText(myTask.getTitle());
        holder.taskDescriptionTextView.setText(myTask.getDescription());
        holder.taskDueDateTextView.setText(myTask.getDueDate().toString());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTitleTextView;
        public TextView taskDescriptionTextView;
        public TextView taskDueDateTextView;
        public TextView taskStatusTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            taskTitleTextView = itemView.findViewById(R.id.textTaskTitle);
            taskDescriptionTextView = itemView.findViewById(R.id.textTaskDescription);
            taskDueDateTextView = itemView.findViewById(R.id.textTaskDueDate);
        }
    }

    public void submitList(List<MyTask> newList) {
        this.taskList = newList;
        notifyDataSetChanged();
    }
}
