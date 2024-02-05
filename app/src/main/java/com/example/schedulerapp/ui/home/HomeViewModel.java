package com.example.schedulerapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schedulerapp.MyClass;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<MyClass>> classListLiveData = new MutableLiveData<>();

    public LiveData<List<MyClass>> getClassListLiveData() {
        return classListLiveData;
    }

    public void addClass(MyClass myClass) {
        List<MyClass> currentList = classListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(myClass);
        classListLiveData.setValue(currentList);
    }

    public void removeClass(MyClass myClass) {
        List<MyClass> currentList = classListLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.remove(myClass);
        classListLiveData.setValue(currentList);
    }

    public void updateClass(MyClass updatedClass) {
        List<MyClass> currentList = classListLiveData.getValue();
        for (int i = 0; i < currentList.size(); i++) {
            MyClass myClass = currentList.get(i);
            if (myClass.getClassName().equals(updatedClass.getClassName())) {
                // Update the class with the new information
                currentList.set(i, updatedClass);
                classListLiveData.setValue(currentList);
                return;
            }
        }
    }
}