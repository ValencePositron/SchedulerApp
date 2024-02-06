package com.example.schedulerapp.ui.slideshow;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.MyClassAdapter;
import com.example.schedulerapp.MyExam;
import com.example.schedulerapp.MyExamAdapter;
import com.example.schedulerapp.R;
import com.example.schedulerapp.databinding.FragmentExamBinding;
import com.example.schedulerapp.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExamFragment extends Fragment {

    private FragmentExamBinding binding;
    private ExamViewModel examViewModel;
    private MyExamAdapter examAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        examViewModel = new ViewModelProvider(this).get(ExamViewModel.class);

        Button btnAddExam = root.findViewById(R.id.btnAddExam);
        btnAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog
                showAddExamDialog();
            }
        });

        Button btnRemoveExam = root.findViewById(R.id.btnRemoveExam);
        btnRemoveExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog for removing an exam
                showRemoveExamDialog();
            }
        });

        Button btnEditExam = root.findViewById(R.id.btnEditExam);
        btnEditExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show the dialog for editing an exam
                showEditExamDialog();
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.examRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<MyExam> initialList = new ArrayList<>();
        examAdapter = new MyExamAdapter(initialList);
        recyclerView.setAdapter(examAdapter);

        // Observe changes in exam list
        examViewModel.getExamListLiveData().observe(getViewLifecycleOwner(), new Observer<List<MyExam>>() {
            @Override
            public void onChanged(List<MyExam> myExams) {
                examAdapter.submitList(myExams);
            }
        });

        return root;
    }

    private void showAddExamDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Exam");

        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_exam, null);
        builder.setView(dialogView);

        // Find views inside the dialog layout
        EditText editTextExamName = dialogView.findViewById(R.id.editTextExamName);
        EditText editTextExamDate = dialogView.findViewById(R.id.editTextExamDate);
        EditText editTextExamTime = dialogView.findViewById(R.id.editTextExamTime);
        EditText editTextExamLocation = dialogView.findViewById(R.id.editTextExamLocation);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Validate input fields
                String examName = editTextExamName.getText().toString().trim();
                String examDate = editTextExamDate.getText().toString().trim();
                String examTime = editTextExamTime.getText().toString().trim();
                String examLocation = editTextExamLocation.getText().toString().trim();

                if (examName.isEmpty() || examDate.isEmpty() || examTime.isEmpty() || examLocation.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit onClick method if any field is empty
                }

                // Create a new MyExam object
                MyExam newExam = new MyExam(examName, examDate, examTime, examLocation);

                // Add the new exam to the ViewModel
                examViewModel.addExam(newExam);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showRemoveExamDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Remove Exam");

        // Get the list of exams from the ViewModel
        List<MyExam> examList = examViewModel.getExamListLiveData().getValue();
        if (examList == null || examList.isEmpty()) {
            Toast.makeText(requireContext(), "No exams to remove", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an array to hold exam names
        CharSequence[] examNames = new CharSequence[examList.size()];
        for (int i = 0; i < examList.size(); i++) {
            examNames[i] = examList.get(i).getTitle();
        }

        // Set up the dialog with the list of exam names
        builder.setItems(examNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove the selected exam from the ViewModel
                examViewModel.removeExam(examList.get(which));
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditExamDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Exam");

        // Get the list of exams from the ViewModel
        List<MyExam> examList = examViewModel.getExamListLiveData().getValue();
        if (examList == null || examList.isEmpty()) {
            Toast.makeText(requireContext(), "No exams to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an array to hold exam names
        CharSequence[] examNames = new CharSequence[examList.size()];
        for (int i = 0; i < examList.size(); i++) {
            examNames[i] = examList.get(i).getTitle();
        }

        // Set up the dialog with the list of exam names
        builder.setItems(examNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the selected exam
                MyExam selectedExam = examList.get(which);

                // Call method to show edit exam info dialog with selected exam information
                showEditExamInfoDialog(selectedExam);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditExamInfoDialog(MyExam examToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Exam Information");

        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_exam_info, null);
        builder.setView(dialogView);

        // Find views inside the dialog layout
        EditText editTextExamName = dialogView.findViewById(R.id.editTextExamName);
        EditText editTextExamDate = dialogView.findViewById(R.id.editTextExamDate);
        EditText editTextExamTime = dialogView.findViewById(R.id.editTextExamTime);
        EditText editTextExamLocation = dialogView.findViewById(R.id.editTextExamLocation);

        // Set initial values in the EditText fields
        editTextExamName.setText(examToEdit.getTitle());
        editTextExamDate.setText(examToEdit.getDate());
        editTextExamTime.setText(examToEdit.getTime());
        editTextExamLocation.setText(examToEdit.getLocation());

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get updated values from EditText fields
                String updatedExamName = editTextExamName.getText().toString().trim();
                String updatedExamDate = editTextExamDate.getText().toString().trim();
                String updatedExamTime = editTextExamTime.getText().toString().trim();
                String updatedExamLocation = editTextExamLocation.getText().toString().trim();

                // Update the selected exam information
                examToEdit.setTitle(updatedExamName);
                examToEdit.setDate(updatedExamDate);
                examToEdit.setTime(updatedExamTime);
                examToEdit.setLocation(updatedExamLocation);

                // Update the exam in the ViewModel
                examViewModel.updateExam(examToEdit);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}