package com.hackfest2018.tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;




public class Splash_j extends Activity {
    public static int SPLASH_TIMEOUT=3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Splash_j.this,nav_home.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIMEOUT);
    }
}
