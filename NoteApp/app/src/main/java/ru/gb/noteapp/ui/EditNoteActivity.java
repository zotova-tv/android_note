package ru.gb.noteapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.Constants;
import ru.gb.noteapp.data.Note;

public class EditNoteActivity extends AppCompatActivity {
    private static final String TAG = "lalala ";
    private EditText title;
    private EditText description;
    private Button saveNote;
    private int id = -1;
    private Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.note_edit_title_text);
        description = findViewById(R.id.note_edit_description_text);
        saveNote = findViewById(R.id.edit_note_update);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(Constants.NOTE))
        {
            note = (Note) intent.getSerializableExtra(Constants.NOTE);
            if(note.getId() != null){
                id = note.getId();
            }
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }

        saveNote.setOnClickListener(v -> saveNoteClick());

    }

    private void saveNoteClick() {
        Log.d(TAG, "saveNoteClick() called");

        if(title.getText().toString().equals(Constants.EMPTY_STRING)){
            Toast.makeText(EditNoteActivity.this, "Please add title", Toast.LENGTH_SHORT).show();
            return;
        }

        note.setTitle(title.getText().toString());
        note.setDescription(description.getText().toString());

        Intent intent = new Intent();
        if(note.getTitle().equals(Constants.EMPTY_STRING) && !note.getDescription().equals(Constants.EMPTY_STRING)){
            note.setTitle("New note");
        }
        if(!note.getTitle().equals(Constants.EMPTY_STRING)){
            intent.putExtra(Constants.NOTE, note);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
