package ru.gb.noteapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.InMemoryRepoImpl;
import ru.gb.noteapp.data.Note;
import ru.gb.noteapp.data.Repo;
import ru.gb.noteapp.recycler.NotesAdapter;

public class NotesListFragment extends Fragment implements NotesAdapter.OnNoteClickListener {

    // private Repo repository = new InMemoryRepoImpl();
    private static final String TAG = "lalala ";
    private Repo repository = InMemoryRepoImpl.getInstance();
    private RecyclerView list;
    private NotesAdapter adapter;
    private Context controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState == null){
            fillRepo();
        }

        adapter = new NotesAdapter();
        adapter.setNotes(repository.getAll());

        adapter.setOnNoteClickListener(this);

        list = view.findViewById(R.id.list);
        // list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list.setLayoutManager(new LinearLayoutManager(getActivity())); // Vertical
        list.setAdapter(adapter);
    }

    private void fillRepo() {
        repository.create(new Note("Title 1", "Description 1"));
        repository.create(new Note("Title 2", "Description 2"));
        repository.create(new Note("Title 3", "Description 3"));
        repository.create(new Note("Title 4", "Description 4"));
        repository.create(new Note("Title 5", "Description 5"));
        repository.create(new Note("Title 6", "Description 6"));
        repository.create(new Note("Title 7", "Description 7"));
        repository.create(new Note("Title 8", "Description 8"));
        repository.create(new Note("Title 9", "Description 9"));
        repository.create(new Note("Title 10", "Description 10"));
        repository.create(new Note("Title 11", "Description 11"));
        repository.create(new Note("Title 12", "Description 12"));
        repository.create(new Note("Title 13", "Description 13"));
        repository.create(new Note("Title 14", "Description 14"));
        repository.create(new Note("Title 15", "Description 15"));
        repository.create(new Note("Title 16", "Description 16"));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected() called with: item = [" + item.getItemId() + "]");
        Fragment fragment;
        switch (item.getItemId())
        {
            case R.id.main_settings:
                Log.d(TAG, "create SettingsFragment()");
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_holder, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_edit_note_holder, new EditNoteFragment())
                        .addToBackStack(null)
                        .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClick(Note note) {
        Log.d(TAG, "onNoteClick() called with: note = [" + note + "]");

        FragmentManager fragmentManager = getChildFragmentManager();
        EditNoteFragment activeFragment = (EditNoteFragment) fragmentManager.findFragmentById(R.id.fragment_edit_note_holder);

        if(activeFragment != null){
            fragmentManager.beginTransaction()
                    .detach(activeFragment)
                    .commit();
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_edit_note_holder, EditNoteFragment.getInstance(note))
                .addToBackStack(null)
                .commit();
    }

    public void updateNotesList(Note note){
        if( note.getId() == null ){
            // addition
            repository.create(note);
            adapter.notifyItemInserted(note.getId());
        }else{
            Log.d(TAG, "updated note = " + note);
            repository.update(note);
            adapter.notifyItemChanged(note.getId());
        }
    }
}