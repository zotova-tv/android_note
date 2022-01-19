package ru.gb.noteapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.Constants;
import ru.gb.noteapp.databinding.ActivityMainDrawerBinding;

public class MainActivity extends AppCompatActivity implements CallerSuperActivityMethods, NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "lalala Main activity";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainDrawerBinding binding;
    private NotesListFragment notesListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        binding = ActivityMainDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        if(savedInstanceState == null){
            notesListFragment = new NotesListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_holder, notesListFragment, Constants.NOTES_LIST_TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        for(Fragment f: getSupportFragmentManager().getFragments()){
            if(f.isVisible()){
                FragmentManager childFm = f.getChildFragmentManager();
                if(childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }else if(f.equals(notesListFragment)){
                    new ConfirmExitDialogFragment().show(getSupportFragmentManager(), null);
                    return;
                }

            }
        }
        super.onBackPressed();
    }

    public void callSuperOnBackPressed(){
        super.onBackPressed();
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
//        switch (item.getItemId()) {
//
//            case R.id.nav_maths: {
//                //do somthing
//                break;
//            }
//        }
//        //close navigation drawer
//        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}