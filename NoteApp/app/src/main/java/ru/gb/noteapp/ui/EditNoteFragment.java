package ru.gb.noteapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.Constants;
import ru.gb.noteapp.data.Note;

public class EditNoteFragment extends Fragment {
    private static final String TAG = "lalala ";
    private EditText title;
    private EditText description;
    private Button saveNote;
    // private int id = -1;
    private Note note;
    private NotesListFragment.NotesListUpdater notesListUpdater;

    public static EditNoteFragment getInstance(Note note){
        EditNoteFragment fragment = new EditNoteFragment();

        Bundle args = new Bundle();
        args.putSerializable(Constants.NOTE, note);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if(context instanceof NotesListFragment.NotesListUpdater){
            notesListUpdater = (NotesListFragment.NotesListUpdater) context;
        }
        else {
            throw new IllegalStateException("Activity must implement NotesListUpdater");
        }
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        title = view.findViewById(R.id.note_edit_title_text);
        description = view.findViewById(R.id.note_edit_description_text);
        saveNote = view.findViewById(R.id.edit_note_update);

        saveNote.setOnClickListener(v -> saveNoteClick());
        Bundle args = getArguments();
        if(args != null && args.containsKey(Constants.NOTE)){
            note = (Note) args.getSerializable(Constants.NOTE);
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }
    }

    private void saveNoteClick() {
        Log.d(TAG, "saveNoteClick() called");

        if(title.getText().toString().equals(Constants.EMPTY_STRING)){
            Toast.makeText(getActivity(), "Please add title", Toast.LENGTH_SHORT).show();
            return;
        }

        if(note == null){
            note = new Note(title.getText().toString(), description.getText().toString());
        }else{
            note.setTitle(title.getText().toString());
            note.setDescription(description.getText().toString());
        }
        notesListUpdater.updateNotesList(note);

        getActivity().getSupportFragmentManager().popBackStack();

    }
}
