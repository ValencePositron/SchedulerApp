package com.example.schedulerapp.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schedulerapp.MyAssignment;
import com.example.schedulerapp.MyClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
            MyAssignment assignment = currentList.get(i);
            if (assignment.getTitle().equals(updatedAssignment.getTitle())) {
                // Update the assignment with the new information
                currentList.set(i, updatedAssignment);
                assignmentListLiveData.setValue(currentList);
                return;
            }
        }
    }

    public void sortAssignmentsByDate() {
        List<MyAssignment> currentList = assignmentListLiveData.getValue();
        if (currentList != null) {
            // Sort the list by due date using a comparator
            Collections.sort(currentList, new Comparator<MyAssignment>() {
                @Override
                public int compare(MyAssignment assignment1, MyAssignment assignment2) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

// Convert date strings to Date objects
                    Date dueDate1 = null;
                    try {
                        dueDate1 = dateFormat.parse(assignment1.getDueDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    Date dueDate2 = null;
                    try {
                        dueDate2 = dateFormat.parse(assignment2.getDueDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

// Compare due dates and return the result
                    return dueDate1.compareTo(dueDate2);
                }
            });
            // Update the LiveData with the sorted list
            assignmentListLiveData.setValue(currentList);
        }
    }

    public void sortAssignmentsByCourse() {
        List<MyAssignment> assignments = assignmentListLiveData.getValue();
        if (assignments != null) {
            Collections.sort(assignments, new Comparator<MyAssignment>() {
                @Override
                public int compare(MyAssignment a1, MyAssignment a2) {
                    return a1.getAssociatedClass().compareToIgnoreCase(a2.getAssociatedClass());
                }
            });
            assignmentListLiveData.setValue(assignments);
        }
    }
}