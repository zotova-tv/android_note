package ru.gb.noteapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.Constants;
import ru.gb.noteapp.databinding.ActivityMainDrawerBinding;

public class MainActivity extends AppCompatActivity implements
        CallerSuperActivityMethods,
        NavigationView.OnNavigationItemSelectedListener,
        ToggleDrawerLayout,
        MainFragmentsController {

    public static final String TAG = "lalala Main activity";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainDrawerBinding binding;
    private NotesListFragment notesListFragment;
    private SettingsFragment settingsFragment;
    private AboutAppFragment aboutAppFragment;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        binding = ActivityMainDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(Constants.SETTINGS_FRAGMENT_TAG);
        aboutAppFragment = (AboutAppFragment) getSupportFragmentManager().findFragmentByTag(Constants.ABOUT_APP_FRAGMENT_TAG);

        if (savedInstanceState == null) {
            notesListFragment = new NotesListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_holder, notesListFragment, Constants.NOTES_LIST_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f.isVisible()) {
                FragmentManager childFm = f.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                } else if (f.equals(notesListFragment)) {
                    new ConfirmExitDialogFragment().show(getSupportFragmentManager(), null);
                    return;
                }

            }
        }
        super.onBackPressed();
    }

    public void callSuperOnBackPressed() {
        super.onBackPressed();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected() called with: item = [" + item + " " + item.getTitle() + "]");
        // R.id.nav_home
        switch (item.getItemId()) {

            case R.id.nav_home: {
                closeAllFragments();
                break;
            }
            case R.id.nav_about_app: {
                addAboutAppFragment();
                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void toggleDrawerLayout() {
        if (drawer.isOpen()) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    public void closeAllFragments() {
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            FragmentManager childFm = f.getChildFragmentManager();
            if (childFm.getBackStackEntryCount() > 0) {
                childFm.popBackStack();
            }
            if (settingsFragment != null) {
                getSupportFragmentManager().popBackStack(Constants.SETTINGS_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            if (aboutAppFragment != null) {
                getSupportFragmentManager().popBackStack(Constants.ABOUT_APP_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    @Override
    public void addSettingsFragment() {
        if(settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_holder,settingsFragment, Constants.SETTINGS_FRAGMENT_TAG)
                .addToBackStack(Constants.SETTINGS_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void addAboutAppFragment() {
        if(aboutAppFragment == null) {
            aboutAppFragment = new AboutAppFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_holder, aboutAppFragment, Constants.ABOUT_APP_FRAGMENT_TAG)
                .addToBackStack(Constants.ABOUT_APP_FRAGMENT_TAG)
                .commit();
    }
}