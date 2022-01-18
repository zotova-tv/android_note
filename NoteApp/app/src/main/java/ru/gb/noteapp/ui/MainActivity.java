package ru.gb.noteapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import ru.gb.noteapp.R;

public class MainActivity extends AppCompatActivity implements CallerSuperActivityMethods {

    public static final String TAG = "lalala Main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_holder, new NotesListFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        for(Fragment f: getSupportFragmentManager().getFragments()){
            if(f.isVisible()){
                FragmentManager childFm = f.getChildFragmentManager();
                if(childFm.getBackStackEntryCount() > 0){
                    childFm.popBackStack();
                    return;
                }else{
                    new ConfirmExitDialogFragment().show(getSupportFragmentManager(), null);
                }
            }
        }

//        super.onBackPressed();
    }

    public void callSuperOnBackPressed(){
        super.onBackPressed();
    }

}