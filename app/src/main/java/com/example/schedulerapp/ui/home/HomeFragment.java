package com.example.schedulerapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.MainActivity;
import com.example.schedulerapp.MyClass;
import com.example.schedulerapp.MyClassAdapter;
import com.example.schedulerapp.R;
import com.example.schedulerapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel classViewModel;
    private MyClassAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        classViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        Button btnAddClass = root.findViewById(R.id.btnAddClass);
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog
                showAddClassDialog();
            }
        });

        Button btnRemoveClass = root.findViewById(R.id.btnRemoveClass);
        btnRemoveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog for removing a class
                showRemoveClassDialog();
            }
        });

        Button btnEditClass = root.findViewById(R.id.btnEditClass);
        btnEditClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog for removing a class
                showEditClassDialog();
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.classRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<MyClass> initialList = new ArrayList<>();
        adapter = new MyClassAdapter(initialList);
        recyclerView.setAdapter(adapter);

        // Observe changes in class list
        classViewModel.getClassListLiveData().observe(getViewLifecycleOwner(), new Observer<List<MyClass>>() {
            @Override
            public void onChanged(List<MyClass> myClasses) {
                adapter.submitList(myClasses);
            }
        });

        return root;
    }

    private void showAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Class");

        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_class, null);
        builder.setView(dialogView);

        // Find views inside the dialog layout
        EditText editTextClassName = dialogView.findViewById(R.id.editTextClassName);
        EditText editTextClassTime = dialogView.findViewById(R.id.editTextClassTime);
        EditText editTextClassLocation = dialogView.findViewById(R.id.editTextClassLocation);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Validate input fields
                String className = editTextClassName.getText().toString().trim();
                String classTime = editTextClassTime.getText().toString().trim();
                String classLocation = editTextClassLocation.getText().toString().trim();

                if (className.isEmpty() || classTime.isEmpty() || classLocation.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit onClick method if any field is empty
                }

                // Create a new MyClass object
                MyClass newClass = new MyClass(className, classTime, classLocation);

                // Add the new class to the ViewModel
                classViewModel.addClass(newClass);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showRemoveClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Remove Class");

        // Get the list of classes from the ViewModel
        List<MyClass> classList = classViewModel.getClassListLiveData().getValue();
        if (classList == null || classList.isEmpty()) {
            Toast.makeText(requireContext(), "No classes to remove", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an array to hold class names
        CharSequence[] classNames = new CharSequence[classList.size()];
        for (int i = 0; i < classList.size(); i++) {
            classNames[i] = classList.get(i).getClassName();
        }

        // Set up the dialog with the list of class names
        builder.setItems(classNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove the selected class from the ViewModel
                classViewModel.removeClass(classList.get(which));
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Class");

        // Get the list of classes from the ViewModel
        List<MyClass> classList = classViewModel.getClassListLiveData().getValue();
        if (classList == null || classList.isEmpty()) {
            Toast.makeText(requireContext(), "No classes to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an array to hold class names
        CharSequence[] classNames = new CharSequence[classList.size()];
        for (int i = 0; i < classList.size(); i++) {
            classNames[i] = classList.get(i).getClassName();
        }

        // Set up the dialog with the list of class names
        builder.setItems(classNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the selected class
                MyClass selectedClass = classList.get(which);

                // Call method to show edit class info dialog with selected class information
                showEditClassInfoDialog(selectedClass);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditClassInfoDialog(MyClass selectedClass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Class Information");

        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_class_info, null);
        builder.setView(dialogView);

        // Find views inside the dialog layout
        EditText editTextClassName = dialogView.findViewById(R.id.editTextClassName);
        EditText editTextClassTime = dialogView.findViewById(R.id.editTextClassTime);
        EditText editTextClassLocation = dialogView.findViewById(R.id.editTextClassLocation);

        // Set initial values in the EditText fields
        editTextClassName.setText(selectedClass.getClassName());
        editTextClassTime.setText(selectedClass.getClassTime());
        editTextClassLocation.setText(selectedClass.getClassLocation());

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get updated values from EditText fields
                String updatedClassName = editTextClassName.getText().toString().trim();
                String updatedClassTime = editTextClassTime.getText().toString().trim();
                String updatedClassLocation = editTextClassLocation.getText().toString().trim();

                // Update the selected class information
                selectedClass.setClassName(updatedClassName);
                selectedClass.setClassTime(updatedClassTime);
                selectedClass.setClassLocation(updatedClassLocation);

                // Update the class in the ViewModel
                classViewModel.updateClass(selectedClass);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}