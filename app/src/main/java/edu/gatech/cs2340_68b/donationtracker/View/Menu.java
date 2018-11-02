package edu.gatech.cs2340_68b.donationtracker.View;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;


import edu.gatech.cs2340_68b.donationtracker.R;

public class Menu extends AppCompatActivity {
    private DrawerLayout nDrawerLayout;
    private ActionBarDrawerToggle aToggle;
    private Toolbar aToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_menu);

        aToolbar = (Toolbar) findViewById(R.id.nav_actionbar);
        setSupportActionBar(aToolbar);

        nDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        aToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open, R.string.close);

        nDrawerLayout.addDrawerListener(aToggle);
        aToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (aToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
