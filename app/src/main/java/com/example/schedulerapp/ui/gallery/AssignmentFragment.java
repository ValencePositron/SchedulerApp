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

import java.util.ArrayList;
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
                String assignmentDate = editTextAssignmentDate.getText().toString().trim();
                String assignmentClass = editTextAssignmentClass.getText().toString().trim();

                if (assignmentName.isEmpty() || assignmentDate.isEmpty() || assignmentClass.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyAssignment newAssignment = new MyAssignment(assignmentName, assignmentDate, assignmentClass);
                assignmentViewModel.addAssignment(newAssignment);
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