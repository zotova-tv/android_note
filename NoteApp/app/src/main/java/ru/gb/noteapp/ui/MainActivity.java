package ru.gb.noteapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.Constants;
import ru.gb.noteapp.data.Note;

public class MainActivity extends AppCompatActivity implements NotesListFragment.NotesListUpdater {

    public static final String TAG = "lalala Main activity";
    private NotesListFragment notesListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        notesListFragment = (NotesListFragment) fragmentManager.findFragmentById(R.id.fragment_notes_list_holder);

        if(notesListFragment == null){
            notesListFragment = new NotesListFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_notes_list_holder, notesListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected() called with: item = [" + item.getItemId() + "]");
        switch (item.getItemId())
        {
            case R.id.main_create:
                Log.d(TAG, "create EditNoteFragment()");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_edit_note_holder, new EditNoteFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateNotesList(Note note) {
        notesListFragment.updateNotesList(note);
    }
}