package com.hackfest2018.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class nav_home extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigation;



    //private static final int[] INPUT_SIZE = {1,12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        Button b=(Button)findViewById(R.id.get_started);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(nav_home.this,MainActivity.class);
                startActivity(i);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.d_layout);
        mDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout, R.string.open, R.string.closed );

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigation =  (NavigationView) findViewById(R.id.navigation);
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch(item.getItemId())
                {   case R.id.nav_home1:
                    Toast.makeText(getApplicationContext(), "Nav_home", Toast.LENGTH_SHORT).show();

                    return true;
                    case R.id.nav_predict:
                        Intent i=new Intent(nav_home.this,MainActivity.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Nav_predict", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_about:
                        Toast.makeText(getApplicationContext(), "Nav_about", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.about);
                        //viewDetails_List();
                        return true;
                    default:
                        return false;
                }
            }
        });

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
