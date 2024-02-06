package com.example.schedulerapp.ui.todolist;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schedulerapp.MyTask;

import java.util.ArrayList;
import java.util.List;

public class ToDoViewModel extends ViewModel {

    private MutableLiveData<List<MyTask>> taskListLiveData = new MutableLiveData<>();

    public LiveData<List<MyTask>> getTaskListLiveData() {
        return taskListLiveData;
    }

    public void addTask(MyTask myTask) {
        List<MyTask> currentList = taskListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(myTask);
        taskListLiveData.setValue(currentList);
    }

    public void removeTask(MyTask myTask) {
        List<MyTask> currentList = taskListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.remove(myTask);
        taskListLiveData.setValue(currentList);
    }

    public void updateTask(MyTask updatedTask) {
        List<MyTask> currentList = taskListLiveData.getValue();
        for (int i = 0; i < currentList.size(); i++) {
            MyTask myTask = currentList.get(i);
            if (myTask.getTitle().equals(updatedTask.getTitle())) {
                // Update the task with the new information
                currentList.set(i, updatedTask);
                taskListLiveData.setValue(currentList);
                return;
            }
        }
    }
}
