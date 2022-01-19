package ru.gb.noteapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ru.gb.noteapp.R;

public class SettingsFragment extends Fragment {

    private static final String TAG = "lalala SettingsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_settings, container, false);
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