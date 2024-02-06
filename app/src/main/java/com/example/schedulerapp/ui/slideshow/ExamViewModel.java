package com.example.schedulerapp.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schedulerapp.MyExam;

import java.util.ArrayList;
import java.util.List;

public class ExamViewModel extends ViewModel {

    private MutableLiveData<List<MyExam>> examListLiveData = new MutableLiveData<>();

    public LiveData<List<MyExam>> getExamListLiveData() {
        return examListLiveData;
    }

    public void addExam(MyExam myExam) {
        List<MyExam> currentList = examListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(myExam);
        examListLiveData.setValue(currentList);
    }

    public void removeExam(MyExam myExam) {
        List<MyExam> currentList = examListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.remove(myExam);
        examListLiveData.setValue(currentList);
    }

    public void updateExam(MyExam updatedExam) {
        List<MyExam> currentList = examListLiveData.getValue();
        for (int i = 0; i < currentList.size(); i++) {
            MyExam myExam = currentList.get(i);
            if (myExam.getTitle().equals(updatedExam.getTitle())) {
                // Update the exam with the new information
                currentList.set(i, updatedExam);
                examListLiveData.setValue(currentList);
                return;
            }
        }
    }
}