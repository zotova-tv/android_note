package ru.gb.noteapp.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepoImpl implements Repo {
    private static final String TAG = "lalala repo";
    private static InMemoryRepoImpl repo;

    public static Repo getInstance()
    {
        if(repo == null)
        {
            repo = new InMemoryRepoImpl();
        }
        return repo;
    }


    private InMemoryRepoImpl()
    {

    }


    private ArrayList<Note> notes = new ArrayList<>();
    private int counter = 0;


    @Override
    public int create(Note note) {
        Log.d(TAG, "create() called with: note = [" + note + "]");
        int id = counter++;
        note.setId(id);
        notes.add(note);
        return id;
    }

    @Override
    public Note read(int id) {
        for(int i = 0; i < notes.size(); i++)
        {
            if(notes.get(i).getId() == id)
                return notes.get(i);
        }
        return null;
    }

    @Override
    public void update(Note note) {
        for(int i = 0; i < notes.size(); i++){
            if(notes.get(i).getId().equals(note.getId())) {
                notes.set(i, note);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for(int i = 0; i < notes.size(); i++)
        {
            if(notes.get(i).getId() == id)
            {
                notes.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        Log.d(TAG, "getAll() called " + notes);
        return notes;
    }
}
