package com.example.schedulerapp.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.MyAssignment;
import com.example.schedulerapp.MyAssignmentAdapter;
import com.example.schedulerapp.R;
import com.example.schedulerapp.databinding.FragmentAssignmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignmentFragment extends Fragment {

    private FragmentAssignmentBinding binding;
    private AssignmentViewModel assignmentViewModel;
    private MyAssignmentAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        binding = FragmentAssignmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btnAddAssignment = root.findViewById(R.id.btnAddAssignment);
        btnAddAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAssignmentDialog();
            }
        });

        Button btnRemoveAssignment = root.findViewById(R.id.btnRemoveAssignment);
        btnRemoveAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemoveAssignmentDialog();
            }
        });

        Button btnEditAssignment = root.findViewById(R.id.btnEditAssignment);
        btnEditAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditAssignmentDialog();
            }
        });

        Button btnSortAssignmentsByDate = root.findViewById(R.id.btnSortByDate);
        btnSortAssignmentsByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentViewModel.sortAssignmentsByDate();
            }
        });

        Button btnSortAssignmentsByCourse = root.findViewById(R.id.btnSortByCourse);
        btnSortAssignmentsByCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentViewModel.sortAssignmentsByCourse();
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.assignmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MyAssignmentAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        assignmentViewModel.getassignmentListLiveData().observe(getViewLifecycleOwner(), assignments -> {
            adapter.setAssignments(assignments);
        });

        return root;
    }

    private void showAddAssignmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Assignment");

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_assignment, null);
        builder.setView(dialogView);

        EditText editTextAssignmentName = dialogView.findViewById(R.id.editTextAssignmentName);
        EditText editTextAssignmentDate = dialogView.findViewById(R.id.editTextAssignmentDate);
        EditText editTextAssignmentClass = dialogView.findViewById(R.id.editTextAssignmentClass);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String assignmentName = editTextAssignmentName.getText().toString().trim();
                String assignmentDateString = editTextAssignmentDate.getText().toString().trim();
                String assignmentClass = editTextAssignmentClass.getText().toString().trim();

                // Validate input fields
                if (assignmentName.isEmpty() || assignmentDateString.isEmpty() || assignmentClass.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate date format
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                dateFormat.setLenient(false);
                try {
                    Date assignmentDate = dateFormat.parse(assignmentDateString);
                    // Date format is valid, create a new assignment
                    MyAssignment newAssignment = new MyAssignment(assignmentName, assignmentDateString, assignmentClass);
                    assignmentViewModel.addAssignment(newAssignment);
                } catch (ParseException e) {
                    // Date format is invalid
                    Toast.makeText(requireContext(), "Please enter a valid date (MM-dd-yyyy)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showRemoveAssignmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Remove Assignment");

        // Get the list of assignments from the ViewModel
        List<MyAssignment> assignmentList = assignmentViewModel.getassignmentListLiveData().getValue();
        if (assignmentList == null || assignmentList.isEmpty()) {
            Toast.makeText(requireContext(), "No assignments to remove", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an array to hold assignment titles
        CharSequence[] assignmentTitles = new CharSequence[assignmentList.size()];
        for (int i = 0; i < assignmentList.size(); i++) {
            assignmentTitles[i] = assignmentList.get(i).getTitle();
        }

        // Set up the dialog with the list of assignment titles
        builder.setItems(assignmentTitles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove the selected assignment from the ViewModel
                assignmentViewModel.removeAssignment(assignmentList.get(which));
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditAssignmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Assignment");

        // Get the list of assignments from the ViewModel
        List<MyAssignment> assignmentList = assignmentViewModel.getassignmentListLiveData().getValue();
        if (assignmentList == null || assignmentList.isEmpty()) {
            Toast.makeText(requireContext(), "No assignments to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an array to hold assignment titles
        CharSequence[] assignmentTitles = new CharSequence[assignmentList.size()];
        for (int i = 0; i < assignmentList.size(); i++) {
            assignmentTitles[i] = assignmentList.get(i).getTitle();
        }

        // Set up the dialog with the list of assignment titles
        builder.setItems(assignmentTitles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the selected assignment
                MyAssignment selectedAssignment = assignmentList.get(which);

                // Call method to show edit assignment info dialog with selected assignment information
                showEditAssignmentInfoDialog(selectedAssignment);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showEditAssignmentInfoDialog(MyAssignment assignmentToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Assignment");

        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_assignment_info, null);
        builder.setView(dialogView);

        // Find views inside the dialog layout
        EditText editTextAssignmentName = dialogView.findViewById(R.id.editTextAssignmentName);
        EditText editTextAssignmentDate = dialogView.findViewById(R.id.editTextAssignmentDate);
        EditText editTextAssignmentClass = dialogView.findViewById(R.id.editTextAssignmentClass);

        // Set the current assignment details in the EditText fields
        editTextAssignmentName.setText(assignmentToEdit.getTitle());
        editTextAssignmentDate.setText(assignmentToEdit.getDueDate());
        editTextAssignmentClass.setText(assignmentToEdit.getAssociatedClass());

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Validate input fields
                String assignmentName = editTextAssignmentName.getText().toString().trim();
                String assignmentDateString = editTextAssignmentDate.getText().toString().trim();
                String assignmentClass = editTextAssignmentClass.getText().toString().trim();

                if (assignmentName.isEmpty() || assignmentDateString.isEmpty() || assignmentClass.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit onClick method if any field is empty
                }

                // Validate date format
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                dateFormat.setLenient(false);
                try {
                    Date assignmentDate = dateFormat.parse(assignmentDateString);
                    // Date format is valid, update the assignment details
                    assignmentToEdit.setTitle(assignmentName);
                    assignmentToEdit.setDueDate(assignmentDateString);
                    assignmentToEdit.setAssociatedClass(assignmentClass);
                    // Update the assignment in the ViewModel
                    assignmentViewModel.updateAssignment(assignmentToEdit);
                } catch (ParseException e) {
                    // Date format is invalid
                    Toast.makeText(requireContext(), "Please enter a valid date (MM-dd-yyyy)", Toast.LENGTH_SHORT).show();
                }
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