package ru.gb.noteapp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ConfirmExitDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Close the app?");
        // builder.setMessage("Are you sure you wand to close the app?");
        builder.setCancelable(true);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Toast.makeText(requireContext(), "Answer is no", Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((CallerSuperActivityMethods) getActivity()).callSuperOnBackPressed();
                Toast.makeText(requireContext(), "NoteApp is closed", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }
}
