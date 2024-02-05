package com.example.schedulerapp.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schedulerapp.MyAssignment;
import com.example.schedulerapp.MyClass;

import java.util.ArrayList;
import java.util.List;

public class AssignmentViewModel extends ViewModel {

    private MutableLiveData<List<MyAssignment>> assignmentListLiveData = new MutableLiveData<>();

    public AssignmentViewModel() {
        assignmentListLiveData = new MutableLiveData<>();
        assignmentListLiveData.setValue(new ArrayList<>()); // Initialize with an empty list
    }
    public LiveData<List<MyAssignment>> getassignmentListLiveData() {
        return assignmentListLiveData;
    }

    public void addAssignment(MyAssignment myAssignment) {
        List<MyAssignment> currentList = assignmentListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(myAssignment);
        assignmentListLiveData.setValue(currentList);
    }

    public void removeAssignment(MyAssignment myAssignment) {
        List<MyAssignment> currentList = assignmentListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.remove(myAssignment);
        assignmentListLiveData.setValue(currentList);
    }

    public void updateAssignment(MyAssignment updatedAssignment) {
        List<MyAssignment> currentList = assignmentListLiveData.getValue();
        for (int i = 0; i < currentList.size(); i++) {
            MyAssignment myAssignment = currentList.get(i);
            if (myAssignment.getTitle().equals(updatedAssignment.getTitle())) {
                // Update the class with the new information
                currentList.set(i, updatedAssignment);
                assignmentListLiveData.setValue(currentList);
                return;
            }
        }
    }
}