package com.example.schedulerapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyClassAdapter extends RecyclerView.Adapter<MyClassAdapter.MyViewHolder> {
    private List<MyClass> classList;

    public MyClassAdapter(List<MyClass> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyClass myClass = classList.get(position);
        holder.classNameTextView.setText(myClass.getClassName());
        holder.classTimeTextView.setText(myClass.getClassTime());
        holder.classLocationTextView.setText(myClass.getClassLocation());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView classNameTextView, classTimeTextView, classLocationTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            classNameTextView = itemView.findViewById(R.id.textClassName);
            classTimeTextView = itemView.findViewById(R.id.textClassTime);
            classLocationTextView = itemView.findViewById(R.id.textClassLocation);
        }
    }
}
