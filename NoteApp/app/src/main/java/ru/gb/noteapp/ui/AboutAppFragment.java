package ru.gb.noteapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ru.gb.noteapp.R;


public class AboutAppFragment extends Fragment {

    private static final String TAG = "lalala AboutAppFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_about_app, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected() called with: item = [" + item.getItemId() + " " + item.getTitle() + "]");
        switch (item.getItemId()) {
            case android.R.id.home:
                ((ToggleDrawerLayout) requireActivity()).toggleDrawerLayout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}