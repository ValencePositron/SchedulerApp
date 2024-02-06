package com.example.schedulerapp.ui.todolist;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulerapp.MyTask;
import com.example.schedulerapp.MyTaskAdapter;
import com.example.schedulerapp.R;
import com.example.schedulerapp.databinding.FragmentHomeBinding;
import com.example.schedulerapp.databinding.FragmentTodolistsBinding;
import com.example.schedulerapp.ui.todolist.ToDoViewModel;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {
    private FragmentTodolistsBinding binding;
    private ToDoViewModel taskViewModel;
    private MyTaskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodolistsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        taskViewModel = new ViewModelProvider(this).get(ToDoViewModel.class);

        Button btnAddTask = root.findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog
                showAddTaskDialog();
            }
        });

        Button btnRemoveTask = root.findViewById(R.id.btnRemoveTask);
        btnRemoveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog for removing a task
                showRemoveTaskDialog();
            }
        });

        Button btnEditTask = root.findViewById(R.id.btnEditTask);
        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog for editing a task
                showEditTaskDialog();
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<MyTask> initialList = new ArrayList<>();
        adapter = new MyTaskAdapter(initialList);
        recyclerView.setAdapter(adapter);

        // Observe changes in task list
        taskViewModel.getTaskListLiveData().observe(getViewLifecycleOwner(), new Observer<List<MyTask>>() {
            @Override
            public void onChanged(List<MyTask> myTasks) {
                adapter.submitList(myTasks);
            }
        });

        return root;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Task");

        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        // Find views inside the dialog layout
        EditText editTextTaskName = dialogView.findViewById(R.id.editTextTaskName);
        EditText editTextTaskDescription = dialogView.findViewById(R.id.editTextTaskDescription);
        EditText editTextTaskDueDate = dialogView.findViewById(R.id.editTextTaskDueDate);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Validate input fields
                String taskName = editTextTaskName.getText().toString().trim();
                String taskDescription = editTextTaskDescription.getText().toString().trim();
                String taskDueDate = editTextTaskDueDate.getText().toString().trim();

                if (taskName.isEmpty() || taskDescription.isEmpty() || taskDueDate.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit onClick method if any field is empty
                }

                // Create a new MyTask object
                MyTask newTask = new MyTask(taskName, taskDescription, taskDueDate);

                // Add the new task to the ViewModel
                taskViewModel.addTask(newTask);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showRemoveTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Remove Task");

        // Get the list of tasks from the ViewModel
        List<MyTask> taskList = taskViewModel.getTaskListLiveData().getValue();
        if (taskList == null || taskList.isEmpty()) {
            Toast.makeText(requireContext(), "No tasks to remove", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an array to hold task names
        CharSequence[] taskNames = new CharSequence[taskList.size()];
        for (int i = 0; i < taskList.size(); i++) {
            taskNames[i] = taskList.get(i).getTitle();
        }

        // Set up the dialog with the list of task names
        builder.setItems(taskNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove the selected task from the ViewModel
                taskViewModel.removeTask(taskList.get(which));
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Task");

        // Get the list of tasks from the ViewModel
        List<MyTask> taskList = taskViewModel.getTaskListLiveData().getValue();
        if (taskList == null || taskList.isEmpty()) {
            Toast.makeText(requireContext(), "No tasks to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an array to hold task names
        CharSequence[] taskNames = new CharSequence[taskList.size()];
        for (int i = 0; i < taskList.size(); i++) {
            taskNames[i] = taskList.get(i).getTitle();
        }

        // Set up the dialog with the list of task names
        builder.setItems(taskNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the selected task
                MyTask selectedTask = taskList.get(which);

                // Call method to show edit task info dialog with selected task information
                showEditTaskInfoDialog(selectedTask);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditTaskInfoDialog(MyTask taskToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Task Information");

        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_task_info, null);
        builder.setView(dialogView);

        // Find views inside the dialog layout
        EditText editTextTaskName = dialogView.findViewById(R.id.editTextTaskName);
        EditText editTextTaskDescription = dialogView.findViewById(R.id.editTextTaskDescription);
        EditText editTextTaskDueDate = dialogView.findViewById(R.id.editTextTaskDueDate);

        // Set initial values in the EditText fields
        editTextTaskName.setText(taskToEdit.getTitle());
        editTextTaskDescription.setText(taskToEdit.getDescription());
        editTextTaskDueDate.setText(taskToEdit.getDueDate());

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get updated values from EditText fields
                String updatedTaskName = editTextTaskName.getText().toString().trim();
                String updatedTaskDescription = editTextTaskDescription.getText().toString().trim();
                String updatedTaskDueDate = editTextTaskDueDate.getText().toString().trim();

                // Update the selected task information
                taskToEdit.setTitle(updatedTaskName);
                taskToEdit.setDescription(updatedTaskDescription);
                taskToEdit.setDueDate(updatedTaskDueDate);

                // Update the task in the ViewModel
                taskViewModel.updateTask(taskToEdit);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
