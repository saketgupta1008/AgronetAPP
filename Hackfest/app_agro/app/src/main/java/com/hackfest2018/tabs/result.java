package com.hackfest2018.tabs;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.hackfest2018.tabs.MainActivity.resu;

/**
 * Created by DELL on 3/25/2018.
 */

public class result extends AppCompatActivity {

    TextView resultt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);


        resultt=(TextView)findViewById(R.id.display);
        resultt.setText(String.valueOf(resu));
    }
}
